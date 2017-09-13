package com.zgq.linechart;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zgq.linechart.R;

/**
 * 自定义折线图
 * 一共需要九组数据，其中第一个和第九个不用来展示，只是为了好看，并且第0个与第一个和第八个与第九个间隔为space，
 * 为了显示好看，建议第0个和最后一个小一点
 */
public class ChartView extends View {

    private static final String TAG = ChartView.class.getSimpleName();
    //一共七组数据，将屏幕x方向均分为7部分，每部分宽度为space，其中最左边和最右边各为二分之一space
    private int space;
    //xy坐标轴颜色
    private int xylinecolor = 0xffe2e2e2;
    //xy坐标轴宽度
    private int xylinewidth = dpToPx(2);
    //xy坐标轴文字颜色
    private int xytextcolor = 0xff7e7e7e;
    //xy坐标轴文字大小
    private int xytextsize = spToPx(12);
    //折线图中折线的颜色
    private int linecolor = 0xff02bbb7;
    //x轴各个坐标点水平间距
    private int interval = dpToPx(50);
    //背景颜色
    private int bgcolor = 0xffffffff;
    //是否在ACTION_UP时，根据速度进行自滑动，没有要求，建议关闭，过于占用GPU
    private boolean isScroll = false;
    //绘制XY轴坐标对应的画笔
    private Paint xyPaint;
    //绘制XY轴的文本对应的画笔
    private Paint xyTextPaint;
    //画折线对应的画笔
    private Paint linePaint;
    private int width;
    private int height;
    //x轴的原点坐标
    private int xOri;
    //y轴的原点坐标
    private int yOri;
    //第一个点X的坐标
    private float xInit;
    //第一个点对应的最大Y坐标
    private float maxXInit;
    //第一个点对应的最小X坐标
    private float minXInit;

    private Map<String, Integer> origiValue = new HashMap<>();
    //x轴坐标对应的数据
    private List<String> xValue = new ArrayList<>();
    //y轴坐标对应的数据
    private List<Integer> yValue = new ArrayList<>();
    //折线对应的数据
    private Map<String, Integer> value = new HashMap<>();
    //点击的点对应的X轴的第几个点，默认1
    private int[] selectIndex = new int[2];
    //X轴刻度文本对应的最大矩形，为了选中时，在x轴文本画的框框大小一致
    private Rect xValueRect;
    //速度检测器
    private VelocityTracker velocityTracker;

    private Context context;

    public ChartView(Context context) {
        this(context, null);
    }

    public ChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
        this.context = context;
        initPaint();
    }

    /**
     * 初始化畫筆
     */
    private void initPaint() {
        xyPaint = new Paint();
        xyPaint.setAntiAlias(true);
        xyPaint.setStrokeWidth(xylinewidth);
        xyPaint.setStrokeCap(Paint.Cap.ROUND);
        xyPaint.setColor(xylinecolor);

        xyTextPaint = new Paint();
        xyTextPaint.setAntiAlias(true);
        xyTextPaint.setTextSize(xytextsize);
        xyTextPaint.setStrokeCap(Paint.Cap.ROUND);
        xyTextPaint.setColor(xytextcolor);
        xyTextPaint.setStyle(Paint.Style.STROKE);

        linePaint = new Paint();
        initLinePaint();
    }

    private void initLinePaint() {
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(xylinewidth);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setColor(linecolor);
        linePaint.setStyle(Paint.Style.FILL);
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.chartView, defStyleAttr, 0);
        int count = array.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = array.getIndex(i);
            if (attr == R.styleable.chartView_xylinecolor) {
                xylinecolor = array.getColor(attr, xylinecolor);

            } else if (attr == R.styleable.chartView_xylinewidth) {
                xylinewidth = (int) array.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, xylinewidth, getResources().getDisplayMetrics()));

            } else if (attr == R.styleable.chartView_xytextcolor) {
                xytextcolor = array.getColor(attr, xytextcolor);

            } else if (attr == R.styleable.chartView_xytextsize) {
                xytextsize = (int) array.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, xytextsize, getResources().getDisplayMetrics()));

            } else if (attr == R.styleable.chartView_linecolor) {
                linecolor = array.getColor(attr, linecolor);

            } else if (attr == R.styleable.chartView_bgcolor) {
                bgcolor = array.getColor(attr, bgcolor);

            } else if (attr == R.styleable.chartView_isScroll) {
                isScroll = array.getBoolean(attr, isScroll);

            }
        }
        array.recycle();

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (changed) {
            //这里需要确定几个基本点，只有确定了xy轴原点坐标，第一个点的X坐标值及其最大最小值
            width = getWidth();
            height = getHeight();
            //计算space
            space = width / 7;
            //计算interval
            interval = width / 7;
            //Y轴文本最大宽度
            float textYWdith = getTextBounds("000", xyTextPaint).width();
            for (int i = 0; i < yValue.size(); i++) {//求取y轴文本最大的宽度
                float temp = getTextBounds(yValue.get(i) + "", xyTextPaint).width();
                if (temp > textYWdith)
                    textYWdith = temp;
            }
            int dp2 = dpToPx(2);
            int dp3 = dpToPx(3);
            //xOri = (int) (dp2 + textYWdith + dp2 + xylinewidth);//dp2是y轴文本距离左边，以及距离y轴的距离
            xOri = 0;
//            //X轴文本最大高度
            xValueRect = getTextBounds("000", xyTextPaint);
            float textXHeight = xValueRect.height();
            for (int i = 0; i < xValue.size(); i++) {//求取x轴文本最大的高度
                Rect rect = getTextBounds(xValue.get(i) + "", xyTextPaint);
                if (rect.height() > textXHeight)
                    textXHeight = rect.height();
                if (rect.width() > xValueRect.width())
                    xValueRect = rect;
            }
            yOri = (int) (height - dp2 - textXHeight - dp3 - xylinewidth - dpToPx(140));//dp3是x轴文本距离底边，dp2是x轴文本距离x轴的距离
            //xInit = interval + xOri;
            xInit = xOri;
//            minXInit = width - (width - xOri) * 0.1f - interval * (xValue.size() - 1);//减去0.1f是因为最后一个X周刻度距离右边的长度为X轴可见长度的10%
            minXInit = width - interval * (xValue.size() - 1);
            maxXInit = xInit;
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.drawColor(bgcolor);
        //drawXY(canvas);
        drawBrokenLineAndPoint(canvas);
    }

    /**
     * 绘制折线和折线交点处对应的点
     *
     * @param canvas
     */
    private void drawBrokenLineAndPoint(Canvas canvas) {
        if (xValue.size() <= 0)
            return;
        //重新开一个图层
        int layerId = canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);
        drawBrokenLine(canvas);
        drawBrokenPoint(canvas);

        // 将折线超出x轴坐标的部分截取掉
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setColor(bgcolor);
        linePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        RectF rectF = new RectF(0, 0, xOri, height);
        canvas.drawRect(rectF, linePaint);
        linePaint.setXfermode(null);
        //保存图层
        canvas.restoreToCount(layerId);
    }

    /**
     * 绘制折线对应的点
     *
     * @param canvas
     */
    private void drawBrokenPoint(Canvas canvas) {
        linePaint.reset();
        float dp2 = dpToPx(2);
        float dp4 = dpToPx(4);
        float dp7 = dpToPx(7);
        //绘制节点对应的原点
        for (int i = 0; i < xValue.size(); i++) {
            float x = xInit + space / 2 + interval * (i - 1);
            float y = yOri - yOri * (1 - 0.1f) * value.get(xValue.get(i)) / yValue.get(yValue.size() - 1);
            if (i == 8) {
                continue;
            }
            //绘制选中的点
            if ((i == (selectIndex[0] - 1)) || (i == (selectIndex[1] - 1))) {
                linePaint.setStyle(Paint.Style.FILL);
                linePaint.setColor(0xffd0f3f2);
                canvas.drawCircle(x, y, dp7, linePaint);
                linePaint.setColor(0xff81dddb);
                canvas.drawCircle(x, y, dp4, linePaint);
                drawFloatTextBox(canvas, x, y - dp7, origiValue.get(xValue.get(i)));
            }
            //绘制普通的节点
            linePaint.setStyle(Paint.Style.FILL);
            linePaint.setColor(Color.WHITE);
            canvas.drawCircle(x, y, dp2, linePaint);
            linePaint.setStyle(Paint.Style.STROKE);
            linePaint.setColor(linecolor);
            canvas.drawCircle(x, y, dp2, linePaint);

        }
    }

    /**
     * 绘制显示Y值的浮动框
     *
     * @param canvas
     * @param x
     * @param y
     * @param text
     */
    private void drawFloatTextBox(Canvas canvas, float x, float y, int text) {
        int dp6 = dpToPx(6);
        int dp18 = dpToPx(18);
        //p1
        Path path = new Path();
        path.moveTo(x, y);
        //p2
        path.lineTo(x - dp6, y - dp6);
        //p3
        path.lineTo(x - dp18, y - dp6);
        //p4
        path.lineTo(x - dp18, y - dp6 - dp18);
        //p5
        path.lineTo(x + dp18, y - dp6 - dp18);
        //p6
        path.lineTo(x + dp18, y - dp6);
        //p7
        path.lineTo(x + dp6, y - dp6);
        //p1
        path.lineTo(x, y);
        canvas.drawPath(path, linePaint);
        linePaint.setColor(Color.WHITE);
        linePaint.setTextSize(spToPx(14));
        Rect rect = getTextBounds(text + "", linePaint);
        canvas.drawText(text + "", x - rect.width() / 2, y - dp6 - (dp18 - rect.height()) / 2, linePaint);
    }

    /**
     * 绘制折线
     *
     * @param canvas
     */
    private void drawBrokenLine(Canvas canvas) {
        initLinePaint();
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(linecolor);
        //绘制折线
        Path backPath = new Path();
        Path linePath = new Path();
        float x = xInit + interval * 0;
        float y = yOri - yOri * (1 - 0.1f) * value.get(xValue.get(0)) / yValue.get(yValue.size() - 1);
        backPath.moveTo(x, y);
        linePath.moveTo(x, y);
        for (int i = 1; i < xValue.size(); i++) {
            if (i == 1) {
                x = xInit + space / 2;
                y = yOri - yOri * (1 - 0.1f) * value.get(xValue.get(i)) / yValue.get(yValue.size() - 1);
                backPath.lineTo(x, y);
                linePath.lineTo(x, y);
                continue;
            }
            if (i == 8) {
                x = xInit + space + interval * (i - 2);
                y = yOri - yOri * (1 - 0.1f) * value.get(xValue.get(i)) / yValue.get(yValue.size() - 1);
                backPath.lineTo(x, y);
                linePath.lineTo(x, y);
                continue;
            }
            x = xInit + space / 2 + interval * (i - 1);
            y = yOri - yOri * (1 - 0.1f) * value.get(xValue.get(i)) / yValue.get(yValue.size() - 1);
            backPath.lineTo(x, y);
            linePath.lineTo(x, y);
        }
        backPath.lineTo(x, getHeight());
        backPath.lineTo(0, getHeight());
        backPath.close();
        canvas.drawPath(linePath, linePaint);
        canvas.drawPath(backPath, getShadeColorPaint(linePaint));
    }

    // 修改笔的颜色
    private Paint getShadeColorPaint(Paint paint) {
        paint.setStyle(Paint.Style.FILL);
        Shader mShader = new LinearGradient(getWidth() / 2, 0, getWidth() / 2, getHeight() - dpToPx(50),
                new int[]{context.getResources().getColor(R.color.colorWhiteTransparent), android.R.color.transparent}, null, Shader.TileMode.CLAMP);
        // 新建一个线性渐变，前两个参数是渐变开始的点坐标，第三四个参数是渐变结束的点的坐标。连接这2个点就拉出一条渐变线了，玩过PS的都懂。然后那个数组是渐变的颜色。下一个参数是渐变颜色的分布，如果为空，每个颜色就是均匀分布的。最后是模式，这里设置的是循环渐变
        paint.setShader(mShader);
        return paint;
    }

    /**
     * 绘制XY坐标
     *
     * @param canvas
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void drawXY(Canvas canvas) {
        int length = dpToPx(4);//刻度的长度
        //绘制Y坐标
        canvas.drawLine(xOri - xylinewidth / 2, 0, xOri - xylinewidth / 2, yOri, xyPaint);
        //绘制y轴箭头
        xyPaint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(xOri - xylinewidth / 2 - dpToPx(5), dpToPx(12));
        path.lineTo(xOri - xylinewidth / 2, xylinewidth / 2);
        path.lineTo(xOri - xylinewidth / 2 + dpToPx(5), dpToPx(12));
        canvas.drawPath(path, xyPaint);
        //绘制y轴刻度
        int yLength = (int) (yOri * (1 - 0.1f) / (yValue.size() - 1));//y轴上面空出10%,计算出y轴刻度间距
        for (int i = 0; i < yValue.size(); i++) {
            //绘制Y轴刻度
            canvas.drawLine(xOri, yOri - yLength * i + xylinewidth / 2, xOri + length, yOri - yLength * i + xylinewidth / 2, xyPaint);
            xyTextPaint.setColor(xytextcolor);
            //绘制Y轴文本
            String text = yValue.get(i) + "";
            Rect rect = getTextBounds(text, xyTextPaint);
            canvas.drawText(text, 0, text.length(), xOri - xylinewidth - dpToPx(2) - rect.width(), yOri - yLength * i + rect.height() / 2, xyTextPaint);
        }
        //绘制X轴坐标
        canvas.drawLine(xOri, yOri + xylinewidth / 2, width, yOri + xylinewidth / 2, xyPaint);
        //绘制x轴箭头
        xyPaint.setStyle(Paint.Style.STROKE);
        path = new Path();
        //整个X轴的长度
        float xLength = xInit + interval * (xValue.size() - 1) + (width - xOri) * 0.1f;
        if (xLength < width)
            xLength = width;
        path.moveTo(xLength - dpToPx(12), yOri + xylinewidth / 2 - dpToPx(5));
        path.lineTo(xLength - xylinewidth / 2, yOri + xylinewidth / 2);
        path.lineTo(xLength - dpToPx(12), yOri + xylinewidth / 2 + dpToPx(5));
        canvas.drawPath(path, xyPaint);
        //绘制x轴刻度
        for (int i = 0; i < xValue.size(); i++) {
            float x = xInit + interval * i;
            if (x >= xOri) {//只绘制从原点开始的区域
                xyTextPaint.setColor(xytextcolor);
                canvas.drawLine(x, yOri, x, yOri - length, xyPaint);
                //绘制X轴文本
                String text = xValue.get(i);
                Rect rect = getTextBounds(text, xyTextPaint);
                if ((i == selectIndex[0] - 1) || (i == selectIndex[1] - 1)) {
                    xyTextPaint.setColor(linecolor);
                    canvas.drawText(text, 0, text.length(), x - rect.width() / 2, yOri + xylinewidth + dpToPx(2) + rect.height(), xyTextPaint);
                    canvas.drawRoundRect(x - xValueRect.width() / 2 - dpToPx(3), yOri + xylinewidth + dpToPx(1), x + xValueRect.width() / 2 + dpToPx(3), yOri + xylinewidth + dpToPx(2) + xValueRect.height() + dpToPx(2), dpToPx(2), dpToPx(2), xyTextPaint);
                } else {
                    canvas.drawText(text, 0, text.length(), x - rect.width() / 2, yOri + xylinewidth + dpToPx(2) + rect.height(), xyTextPaint);
                }
            }
        }
    }

    public int[] getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int[] selectIndex) {
        this.selectIndex = selectIndex;
        invalidate();
    }

    public void setxValue(List<String> xValue) {
        this.xValue = xValue;
    }

    public void setyValue(List<Integer> yValue) {
        this.yValue = yValue;
        invalidate();
    }

    public void setValue(Map<String, Integer> value) {
        this.value = value;
        invalidate();
    }

    public void setValue(Map<String, Integer> value, List<String> xValue, List<Integer> yValue, int normalize) {
        normalizeValue(value,xValue,yValue,normalize);
        int indexMax = 0;
        int tmp = 0;
        for (int i = 0; i < xValue.size(); i++) {
            if (value.get(xValue.get(i)) > tmp) {
                tmp = value.get(xValue.get(i));
                indexMax = i;
            }
        }
        int[] selected = new int[2];
        selected[0] = indexMax + 1;
        selected[1] = 8;
        setSelectIndex(selected);
        invalidate();
    }

    private void normalizeValue(Map<String, Integer> value, List<String> xValue, List<Integer> yValue, int normalize){
        int max = 0;
        for (int i = 0; i < yValue.size(); i++) {
            int tmp = value.get(xValue.get(i));
            if (tmp > max) {
                max = tmp;
            }
        }
        float scale = 0;
        if (max != 0) {
            scale = normalize / (float)max ;
        }

        for (int i = 0; i < xValue.size(); i++) {
            origiValue.put(xValue.get(i), value.get(xValue.get(i)));
            int valueGraph = (int)(value.get(xValue.get(i)) * scale);
            value.put(xValue.get(i), valueGraph);
        }

        this.value = value;
        this.xValue = xValue;
        this.yValue = yValue;
    }

    public List<String> getxValue() {
        return xValue;
    }

    public List<Integer> getyValue() {
        return yValue;
    }

    public Map<String, Integer> getValue() {
        return value;
    }

    /**
     * 获取丈量文本的矩形
     *
     * @param text
     * @param paint
     * @return
     */
    private Rect getTextBounds(String text, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect;
    }

    /**
     * dp转化成为px
     *
     * @param dp
     * @return
     */
    private int dpToPx(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f * (dp >= 0 ? 1 : -1));
    }

    /**
     * sp转化为px
     *
     * @param sp
     * @return
     */
    private int spToPx(int sp) {
        float scaledDensity = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (scaledDensity * sp + 0.5f * (sp >= 0 ? 1 : -1));
    }
}