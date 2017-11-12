package com.zgq.rim.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.view.MotionEventCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import com.zgq.rim.R;
import com.zgq.rim.util.MeasureUtil;

public class SimpleButton extends View {

    // 3种外形模式：圆角矩形、圆弧、直角矩形
    public final static int SHAPE_ROUND_RECT = 101;
    public final static int SHAPE_ARC = 102;
    public final static int SHAPE_RECT = 103;
    // 类型模式：正常、可选中、图标选中消失、图标选中切换
    public final static int MODE_NORMAL = 201;
    public final static int MODE_CHECK = 202;
    public final static int MODE_ICON_CHECK_INVISIBLE = 203;
    public final static int MODE_ICON_CHECK_CHANGE = 204;

    // 显示外形
    private int tagShape = SHAPE_ROUND_RECT;
    // 显示类型
    private int tagMode = MODE_NORMAL;
    // 画笔
    private Paint paint;
    // 背景色
    private int bgColor = Color.WHITE;
    // 边框颜色
    private int borderColor = Color.parseColor("#ff333333");
    // 原始标签颜色
    private int textColor = Color.parseColor("#ff666666");
    // 选中状态背景色
    private int bgColorChecked = Color.WHITE;
    // 选中状态边框颜色
    private int borderColorChecked = Color.parseColor("#ff333333");
    // 选中状态字体颜色
    private int textColorChecked = Color.parseColor("#ff666666");
    // 遮罩颜色
    private int scrimColor = Color.argb(0x66, 0xc0, 0xc0, 0xc0);
    // 字体大小
    private float textSize;
    // 字体宽度和高度
    private int fontLen;
    private int fontH;
    private int fontLenChecked;
    // 基线偏移距离
    private float baseLineDistance;
    // 边框大小
    private float borderWidth;
    // 边框角半径
    private float radius;
    // 内容
    private String text;
    // 选中时内容
    private String textChecked;
    // 显示的文字
    private String showText;
    // 字体水平空隙
    private int horizontalPadding;
    // 字体垂直空隙
    private int verticalPadding;
    // 边框矩形
    private RectF rect;
    // 装饰的icon
    private Drawable decorateIcon;
    // 变化模式下的icon
    private Drawable decorateIconChange;
    // 设置图标的位置，只支持左右两边
    private int iconGravity = Gravity.LEFT;
    // icon和文字间距
    private int iconPadding = 0;
    // icon大小
    private int iconSize = 0;
    // 是否选中
    private boolean isChecked = false;
    // 是否自动切换选中状态，不使能可以灵活地选择切换，通过用于等待网络返回再做切换
    private boolean isAutoToggleCheck = false;
    // 是否被按住
    private boolean isPressed = false;


    public SimpleButton(Context context) {
        this(context, null);
    }

    public SimpleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        _init(context, attrs);
    }

    private void _init(Context context, AttributeSet attrs) {
        borderWidth = MeasureUtil.dp2px(context, 0.5f);
        radius = MeasureUtil.dp2px(context, 5f);
        horizontalPadding = (int) MeasureUtil.dp2px(context, 5f);
        verticalPadding = (int) MeasureUtil.dp2px(context, 5f);
        iconPadding = (int) MeasureUtil.dp2px(context, 3f);
        textSize = MeasureUtil.sp2px(context, 14f);

        if (attrs != null) {
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SimpleButton);
            try {
                tagShape = a.getInteger(R.styleable.SimpleButton_sb_shape,
                        SimpleButton.SHAPE_ROUND_RECT);
                tagMode = a.getInteger(R.styleable.SimpleButton_sb_mode, MODE_NORMAL);
                if (tagMode == MODE_CHECK
                        || tagMode == MODE_ICON_CHECK_INVISIBLE
                        || tagMode == MODE_ICON_CHECK_CHANGE) {
                    isAutoToggleCheck = true;
                    isChecked = a.getBoolean(R.styleable.SimpleButton_sb_checked, false);
                    decorateIconChange = a.getDrawable(R.styleable.SimpleButton_sb_icon_change);
                }
                isAutoToggleCheck = a.getBoolean(
                        R.styleable.SimpleButton_sb_auto_check, isAutoToggleCheck);

                text = a.getString(R.styleable.SimpleButton_sb_text);
                textChecked = a.getString(R.styleable.SimpleButton_sb_text_check);
                textSize = a.getDimension(R.styleable.SimpleButton_sb_text_size, textSize);
                bgColor = a.getColor(R.styleable.SimpleButton_sb_bg_color, Color.WHITE);
                borderColor = a.getColor(R.styleable.SimpleButton_sb_border_color,
                        Color.parseColor("#ff333333"));
                textColor = a.getColor(R.styleable.SimpleButton_sb_text_color,
                        Color.parseColor("#ff666666"));
                bgColorChecked = a.getColor(R.styleable.SimpleButton_sb_bg_color_check, bgColor);
                borderColorChecked = a.getColor(R.styleable.SimpleButton_sb_border_color_check,
                        borderColor);
                textColorChecked = a.getColor(R.styleable.SimpleButton_sb_text_color_check,
                        textColor);
                borderWidth = a.getDimension(R.styleable.SimpleButton_sb_border_width,
                        borderWidth);
                radius = a.getDimension(R.styleable.SimpleButton_sb_border_radius, radius);
                horizontalPadding = (int) a.getDimension(
                        R.styleable.SimpleButton_sb_horizontal_padding, horizontalPadding);
                verticalPadding = (int) a.getDimension(
                        R.styleable.SimpleButton_sb_vertical_padding, verticalPadding);
                iconPadding = (int) a.getDimension(
                        R.styleable.SimpleButton_sb_icon_padding, iconPadding);
                decorateIcon = a.getDrawable(R.styleable.SimpleButton_sb_icon);
                iconGravity = a.getInteger(R.styleable.SimpleButton_sb_gravity, Gravity.LEFT);
            } finally {
                a.recycle();
            }
        }

        if (tagMode == MODE_ICON_CHECK_CHANGE && decorateIconChange == null) {
            throw new RuntimeException("You must set the drawable by 'tag_icon_change' " +
                    "property in MODE_ICON_CHECK_CHANGE mode");
        }
        // 如果没有图标则把 mIconPadding 设为0
        if (decorateIcon == null && decorateIconChange == null) {
            iconPadding = 0;
        }
        rect = new RectF();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setClickable(true);
    }

    private int _adjustText(int maxWidth) {
        if (paint.getTextSize() != textSize) {
            paint.setTextSize(textSize);
            final Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            // 文字高度
            fontH = (int) (fontMetrics.descent - fontMetrics.ascent);
            // 用来设置基线的偏移量,再加上 getHeight() / 2 就是基线坐标，尼玛折腾了我好久
            baseLineDistance = (int) Math.ceil((fontMetrics.descent - fontMetrics.ascent)
                    / 2 - fontMetrics.descent);
        }
        // 计算文字宽度
        if (TextUtils.isEmpty(text)) {
            text = "";
        }
        fontLen = (int) paint.measureText(text);
        if (TextUtils.isEmpty(textChecked)) {
            fontLenChecked = fontLen;
        } else {
            fontLenChecked = (int) paint.measureText(textChecked);
        }
        // 计算图标大小
        if ((decorateIcon != null || decorateIconChange != null) && iconSize != fontH) {
            iconSize = fontH;
        }
        // 计算出了文字外所需要占用的宽度
        int allPadding;
        if (tagMode == MODE_ICON_CHECK_INVISIBLE && isChecked) {
            allPadding = horizontalPadding * 2;
        } else if (decorateIcon == null && !isChecked) {
            allPadding = horizontalPadding * 2;
        } else {
            allPadding = iconPadding + iconSize + horizontalPadding * 2;
        }
        // 设置显示的文字
        if (isChecked && !TextUtils.isEmpty(textChecked)) {
            if (fontLenChecked + allPadding > maxWidth) {
                float pointWidth = paint.measureText(".");
                // 计算能显示的字体长度
                float maxTextWidth = maxWidth - allPadding - pointWidth * 3;
                showText = _clipShowText(textChecked, paint, maxTextWidth);
                fontLenChecked = (int) paint.measureText(showText);
            } else {
                showText = textChecked;
            }
        } else if (fontLen + allPadding > maxWidth) {
            float pointWidth = paint.measureText(".");
            // 计算能显示的字体长度
            float maxTextWidth = maxWidth - allPadding - pointWidth * 3;
            showText = _clipShowText(text, paint, maxTextWidth);
            fontLen = (int) paint.measureText(showText);
        } else {
            showText = text;
        }

        return allPadding;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int allPadding = _adjustText(getMeasuredWidth());
        int fontLen = isChecked ? fontLenChecked : this.fontLen;
        // 如果为精确测量 MeasureSpec.EXACTLY，则直接使用测量的大小，否则让控件实现自适应
        // 如果你用了精确测量则 mHorizontalPadding 和 mVerticalPadding 会对最终大小判定无效
        int width = (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) ?
                MeasureSpec.getSize(widthMeasureSpec) : allPadding + fontLen;
        int height = (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) ?
                MeasureSpec.getSize(heightMeasureSpec) : verticalPadding * 2 + fontH;
        setMeasuredDimension(width, height);
        // 计算图标放置位置
        if (decorateIcon != null || decorateIconChange != null) {
            int top = (height - iconSize) / 2;
            int left;
            if (iconGravity == Gravity.RIGHT) {
                int padding = (width - iconSize - fontLen - iconPadding) / 2;
                left = width - padding - iconSize;
            } else {
                left = (width - iconSize - fontLen - iconPadding) / 2;
            }
            if (tagMode == MODE_ICON_CHECK_CHANGE && isChecked && decorateIconChange != null) {
                decorateIconChange.setBounds(left, top, iconSize + left, iconSize + top);
            } else if (decorateIcon != null) {
                decorateIcon.setBounds(left, top, iconSize + left, iconSize + top);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 设置矩形边框
        rect.set(borderWidth, borderWidth, w - borderWidth, h - borderWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 圆角
        float radius = this.radius;
        if (tagShape == SHAPE_ARC) {
            radius = rect.height() / 2;
        } else if (tagShape == SHAPE_RECT) {
            radius = 0;
        }
        // 绘制背景
        paint.setStyle(Paint.Style.FILL);
        if (isChecked) {
            paint.setColor(bgColorChecked);
        } else {
            paint.setColor(bgColor);
        }
        canvas.drawRoundRect(rect, radius, radius, paint);
        // 绘制边框
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderWidth);
        if (isChecked) {
            paint.setColor(borderColorChecked);
        } else {
            paint.setColor(borderColor);
        }
        canvas.drawRoundRect(rect, radius, radius, paint);
        // 绘制文字
        paint.setStyle(Paint.Style.FILL);
        if (isChecked) {
            paint.setColor(textColorChecked);
            int padding = tagMode == MODE_ICON_CHECK_INVISIBLE ? 0 : iconSize + iconPadding;
            canvas.drawText(showText,
                    iconGravity == Gravity.RIGHT ? (getWidth() - fontLenChecked - padding) / 2
                            : (getWidth() - fontLenChecked - padding) / 2 + padding,
                    getHeight() / 2 + baseLineDistance, paint);
        } else {
            paint.setColor(textColor);
            int padding = decorateIcon == null ? 0 : iconSize + iconPadding;
            canvas.drawText(showText,
                    iconGravity == Gravity.RIGHT ? (getWidth() - fontLen - padding) / 2
                            : (getWidth() - fontLen - padding) / 2 + padding,
                    getHeight() / 2 + baseLineDistance, paint);
        }
        // 绘制Icon
        if (tagMode == MODE_ICON_CHECK_CHANGE && isChecked && decorateIconChange != null) {
            decorateIconChange.setColorFilter(paint.getColor(), PorterDuff.Mode.SRC_IN);
            decorateIconChange.draw(canvas);
        } else if (tagMode == MODE_ICON_CHECK_INVISIBLE && isChecked) {
            // Don't need to draw
        } else if (decorateIcon != null) {
            decorateIcon.setColorFilter(paint.getColor(), PorterDuff.Mode.SRC_IN);
            decorateIcon.draw(canvas);
        }
        // 绘制半透明遮罩
        if (isPressed) {
            paint.setColor(scrimColor);
            canvas.drawRoundRect(rect, radius, radius, paint);
        }
    }

    /**
     * ==================================== 触摸点击控制 ====================================
     */

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (MotionEventCompat.getActionMasked(event)) {
            case MotionEvent.ACTION_DOWN:
                isPressed = true;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isPressed && !_isViewUnder(event.getX(), event.getY())) {
                    isPressed = false;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (_isViewUnder(event.getX(), event.getY())) {
                    _toggleTagCheckStatus();
                }
            case MotionEvent.ACTION_CANCEL:
                if (isPressed) {
                    isPressed = false;
                    invalidate();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 判断是否在 Tag 控件内
     *
     * @param x
     * @param y
     * @return
     */
    private boolean _isViewUnder(float x, float y) {
        return x >= 0 && x < getWidth() &&
                y >= 0 && y < getHeight();
    }

    /**
     * 切换tag选中状态
     */
    private void _toggleTagCheckStatus() {
        if (isAutoToggleCheck) {
            setChecked(!isChecked);
        }
    }

    public boolean isChecked() {
        return isChecked;
    }

    /**
     * 设置选中状态
     *
     * @param checked
     */
    public void setChecked(boolean checked) {
        if (isChecked == checked) {
            return;
        }
        isChecked = checked;
        // 注意，这里用 requestLayout() 而不是只用 invalidate()，
        // 因为 invalidate() 没法回调 onMeasure() 进行测量，
        // 如果控件自适应大小的话是有可能改变大小的，所以加上 requestLayout()
        requestLayout();
        invalidate();
        if (mCheckListener != null) {
            mCheckListener.onCheckedChanged(isChecked);
        }
    }

    /**
     * 裁剪Text
     *
     * @param oriText
     * @param paint
     * @param maxTextWidth
     * @return
     */
    private String _clipShowText(String oriText, Paint paint, float maxTextWidth) {
        float tmpWidth = 0;
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < oriText.length(); i++) {
            char c = oriText.charAt(i);
            float cWidth = paint.measureText(String.valueOf(c));
            // 计算每个字符的宽度之和，如果超过能显示的长度则退出
            if (tmpWidth + cWidth > maxTextWidth) {
                break;
            }
            strBuilder.append(c);
            tmpWidth += cWidth;
        }
        // 末尾添加3个.并设置为显示字符
        strBuilder.append("...");
        return strBuilder.toString();
    }

    /**
     * ==================================== 接口 ====================================
     */

    public int getTagShape() {
        return tagShape;
    }

    public void setTagShape(int tagShape) {
        this.tagShape = tagShape;
        _update();
    }

    public int getTagMode() {
        return tagMode;
    }

    public void setTagMode(int tagMode) {
        this.tagMode = tagMode;
        _update();
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
        // 设置颜色调用这个就行，回调onDraw()
        invalidate();
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        invalidate();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        invalidate();
    }

    public int getBgColorChecked() {
        return bgColorChecked;
    }

    public void setBgColorChecked(int bgColorChecked) {
        this.bgColorChecked = bgColorChecked;
        invalidate();
    }

    public int getBorderColorChecked() {
        return borderColorChecked;
    }

    public void setBorderColorChecked(int borderColorChecked) {
        this.borderColorChecked = borderColorChecked;
        invalidate();
    }

    public int getTextColorChecked() {
        return textColorChecked;
    }

    public void setTextColorChecked(int textColorChecked) {
        this.textColorChecked = textColorChecked;
        invalidate();
    }

    public int getScrimColor() {
        return scrimColor;
    }

    public void setScrimColor(int scrimColor) {
        this.scrimColor = scrimColor;
        invalidate();
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public float getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
        invalidate();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        _update();
    }

    public String getTextChecked() {
        return textChecked;
    }

    public void setTextChecked(String textChecked) {
        this.textChecked = textChecked;
        _update();
    }

    public int getHorizontalPadding() {
        return horizontalPadding;
    }

    public void setHorizontalPadding(int horizontalPadding) {
        this.horizontalPadding = horizontalPadding;
        _update();
    }

    public int getVerticalPadding() {
        return verticalPadding;
    }

    public void setVerticalPadding(int verticalPadding) {
        this.verticalPadding = verticalPadding;
        _update();
    }

    public Drawable getDecorateIcon() {
        return decorateIcon;
    }

    public void setDecorateIcon(Drawable decorateIcon) {
        this.decorateIcon = decorateIcon;
        _update();
    }

    public Drawable getDecorateIconChange() {
        return decorateIconChange;
    }

    public void setDecorateIconChange(Drawable decorateIconChange) {
        this.decorateIconChange = decorateIconChange;
        _update();
    }

    public int getIconPadding() {
        return iconPadding;
    }

    public void setIconPadding(int iconPadding) {
        this.iconPadding = iconPadding;
        _update();
    }

    public boolean isAutoToggleCheck() {
        return isAutoToggleCheck;
    }

    public void setAutoToggleCheck(boolean isAutoToggleCheck) {
        this.isAutoToggleCheck = isAutoToggleCheck;
    }

    /**
     * 调用这些接口进行属性设置如果最后可能会改变按钮的大小的话最后调用一下这个接口，
     * 以刷新界面，建议属性直接在布局里设置
     * 只需要回调onDraw()的话调用invalidate()就可以了
     */
    private void _update() {
        requestLayout();
        invalidate();
    }

    /**
     * ==================================== 点击监听 ====================================
     */

    private OnCheckedChangeListener mCheckListener;


    public void setCheckListener(OnCheckedChangeListener onCheckedChangeListener) {
        mCheckListener = onCheckedChangeListener;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(boolean isChecked);
    }
}
