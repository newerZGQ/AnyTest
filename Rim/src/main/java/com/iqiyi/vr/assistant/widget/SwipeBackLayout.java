package com.iqiyi.vr.assistant.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.iqiyi.vr.assistant.R;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 * Created by wangyancong on 2017/8/23.
 * 开源项目：https://github.com/ikew0ng/SwipeBackLayout
 */
public class SwipeBackLayout extends FrameLayout {

    // 最小快速滑动速度
    private static final int MIN_FLING_VELOCITY = 400; // dips per second
    // 默认遮罩颜色
    private static final int DEFAULT_SCRIM_COLOR = 0x99000000;
    // 透明度基数
    private static final int FULL_ALPHA = 255;
    // 默认滚动退出临界值，超过这个值就滑动退出
    private static final float DEFAULT_SCROLL_THRESHOLD = 0.3f;
    // 超出滚动距离，就是在 activity 从界面滑出它的宽或高的基础上在多滑动 OVER_SCROLL_DISTANCE，这个应该是让滑出效果体验更好
    private static final int OVER_SCROLL_DISTANCE = 10;
    /**
     * 边缘拖拽使能标志位，支持左右和下边缘
     * 暂时不支持 EDGE_ALL，滑动会有问题，详见 @see {@link ViewDragCallback#clampViewPositionHorizontal}
     */
    public static final int EDGE_INVALID = -1;
    public static final int EDGE_LEFT = ViewDragHelper.EDGE_LEFT;
    public static final int EDGE_RIGHT = ViewDragHelper.EDGE_RIGHT;
    public static final int EDGE_BOTTOM = ViewDragHelper.EDGE_BOTTOM;
    // @see {@link #writeFile(String, InputStream, boolean)}
    public static final int EDGE_ALL = EDGE_LEFT | EDGE_RIGHT | EDGE_BOTTOM;

    // 拖拽标志
    private int edgeFlag;
    // 滚动临界值
    private float scrollThreshold = DEFAULT_SCROLL_THRESHOLD;
    // 绑定的Activity
    private Activity attachActivity;
    // 使能
    private boolean enableScroll = true;
    // Activity的ContentView
    private View contentView;
    // 拖拽帮助类
    private ViewDragHelper dragHelper;
    // 滚动百分比{0~1}
    private float scrollPercent;
    // ContentView的左偏移
    private int contentLeft;
    // ContentView的上偏移
    private int contentTop;
    // 左边缘阴影
    private Drawable shadowLeft;
    // 右边缘阴影
    private Drawable shadowRight;
    // 下边缘阴影
    private Drawable shadowBottom;
    // 遮罩不透明度百分比
    private float scrimOpacity;
    // 遮罩颜色
    private int scrimColor = DEFAULT_SCRIM_COLOR;
    // 是否正在处理onLayout()
    private boolean inLayout;
    //
    private Rect contentRect = new Rect();
    // 拖拽边缘
    private int trackingEdge = EDGE_INVALID;


    public SwipeBackLayout(Context context) {
        this(context, null);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        _init();
    }

    private void _init() {
        dragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragCallback());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        inLayout = true;
        if (contentView != null) {
            contentView.layout(contentLeft, contentTop,
                    contentLeft + contentView.getMeasuredWidth(),
                    contentTop + contentView.getMeasuredHeight());
        }
        inLayout = false;
    }

    @Override
    public void requestLayout() {
        if (!inLayout) {
            super.requestLayout();
        }
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        final boolean drawContent = child == contentView;
        // 绘制子控件
        boolean ret = super.drawChild(canvas, child, drawingTime);
        if (scrimOpacity > 0 && drawContent
                && dragHelper.getViewDragState() != ViewDragHelper.STATE_IDLE) {
            // 根据拖拽状态来绘制阴影和遮罩
            _drawShadow(canvas, child);
            _drawScrim(canvas, child);
        }
        return ret;
    }

    /**
     * 绘制遮罩
     *
     * @param canvas
     * @param child
     */
    private void _drawScrim(Canvas canvas, View child) {
        // 获取遮罩颜色的基础透明度，>>>为无符号右移，因为透明度保存在最高位字节里0xff000000，所以不能用 >>
        final int baseAlpha = (scrimColor & 0xff000000) >>> 24;
        // 在基础透明度的基础上根据 mScrimOpacity 来改变透明度
        final int alpha = (int) (baseAlpha * scrimOpacity);
        // 遮罩的最终显示颜色
        final int color = alpha << 24 | (scrimColor & 0xffffff);
        // 绘制遮罩，clipRect裁剪出遮罩大小
        if ((trackingEdge & EDGE_LEFT) != 0) {
            canvas.clipRect(0, 0, child.getLeft(), getHeight());
        } else if ((trackingEdge & EDGE_RIGHT) != 0) {
            canvas.clipRect(child.getRight(), 0, getRight(), getHeight());
        } else if ((trackingEdge & EDGE_BOTTOM) != 0) {
            canvas.clipRect(child.getLeft(), child.getBottom(), getRight(), getHeight());
        }
        canvas.drawColor(color);
    }

    /**
     * 绘制阴影
     *
     * @param canvas
     * @param child
     */
    private void _drawShadow(Canvas canvas, View child) {
        child.getHitRect(contentRect);

        if ((edgeFlag & EDGE_LEFT) != 0) {
            shadowLeft.setBounds(contentRect.left - shadowLeft.getIntrinsicWidth(), contentRect.top,
                    contentRect.left, contentRect.bottom);
            shadowLeft.setAlpha((int) (scrimOpacity * FULL_ALPHA));
            shadowLeft.draw(canvas);
        }

        if ((edgeFlag & EDGE_RIGHT) != 0) {
            shadowRight.setBounds(contentRect.right, contentRect.top,
                    contentRect.right + shadowRight.getIntrinsicWidth(), contentRect.bottom);
            shadowRight.setAlpha((int) (scrimOpacity * FULL_ALPHA));
            shadowRight.draw(canvas);
        }

        if ((edgeFlag & EDGE_BOTTOM) != 0) {
            shadowBottom.setBounds(contentRect.left, contentRect.bottom, contentRect.right,
                    contentRect.bottom + shadowBottom.getIntrinsicHeight());
            shadowBottom.setAlpha((int) (scrimOpacity * FULL_ALPHA));
            shadowBottom.draw(canvas);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!enableScroll) {
            return false;
        }
        try {
            return dragHelper.shouldInterceptTouchEvent(event);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!enableScroll) {
            return false;
        }
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        scrimOpacity = 1 - scrollPercent;
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }


    private class ViewDragCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            boolean edgeTouched = dragHelper.isEdgeTouched(edgeFlag, pointerId);
            if (edgeTouched) {
                if (dragHelper.isEdgeTouched(EDGE_LEFT, pointerId)) {
                    trackingEdge = EDGE_LEFT;
                } else if (dragHelper.isEdgeTouched(EDGE_RIGHT, pointerId)) {
                    trackingEdge = EDGE_RIGHT;
                } else if (dragHelper.isEdgeTouched(EDGE_BOTTOM, pointerId)) {
                    trackingEdge = EDGE_BOTTOM;
                }
            }
            boolean directionCheck = false;
            if (edgeFlag == EDGE_LEFT || edgeFlag == EDGE_RIGHT) {
                // 左右边缘则检测竖直方向的滑动
                directionCheck = !dragHelper.checkTouchSlop(ViewDragHelper.DIRECTION_VERTICAL, pointerId);
            } else if (edgeFlag == EDGE_BOTTOM) {
                // 下边缘则检测水平方向的滑动
                directionCheck = !dragHelper.checkTouchSlop(ViewDragHelper.DIRECTION_HORIZONTAL, pointerId);
            } else if (edgeFlag == EDGE_ALL) {
                directionCheck = true;
            }
            return edgeTouched && directionCheck;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            // 返回0则不能水平滑动
            return edgeFlag & (EDGE_LEFT | EDGE_RIGHT);
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            // 返回0则不能垂直滑动
            return edgeFlag & EDGE_BOTTOM;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            int ret = 0;
            /**
             * 注意：原开源项目中的 ViewDragHelper 是自己写的，感兴趣的可以对比两者中的 shouldInterceptTouchEvent() 方法的区别;
             * 这里和原开源项目处理不同，用 mEdgeFlag 判断而不是 mTrackingEdge，因为系统的 clampViewPositionHorizontal()
             * 会先于 tryCaptureView() 调用，且如果水平方向上这里没发生移动就不会调用 tryCaptureView()，mTrackingEdge 是在
             * tryCaptureView() 赋值的，所以这边要做些判断
             */
            // 这里控制 ContentView 的水平滑动范围
            int edgeFlag = trackingEdge == EDGE_INVALID ? SwipeBackLayout.this.edgeFlag : trackingEdge;
            if ((edgeFlag & EDGE_LEFT) != 0) {
                ret = Math.min(child.getWidth(), Math.max(left, 0));
            } else if ((edgeFlag & EDGE_RIGHT) != 0) {
                ret = Math.min(0, Math.max(left, -child.getWidth()));
            }
            return ret;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            int ret = 0;
            // 这里控制 ContentView 的垂直滑动范围
            int edgeFlag = trackingEdge == EDGE_INVALID ? SwipeBackLayout.this.edgeFlag : trackingEdge;
            if ((edgeFlag & EDGE_BOTTOM) != 0) {
                ret = Math.min(0, Math.max(top, -child.getHeight()));
            }
            return ret;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if ((trackingEdge & EDGE_LEFT) != 0) {
                scrollPercent = Math.abs((float) left / (contentView.getWidth() + shadowLeft.getIntrinsicWidth()));
            } else if ((trackingEdge & EDGE_RIGHT) != 0) {
                scrollPercent = Math.abs((float) left / (contentView.getWidth() + shadowRight.getIntrinsicWidth()));
            } else if ((trackingEdge & EDGE_BOTTOM) != 0) {
                scrollPercent = Math.abs((float) top / (contentView.getHeight() + shadowBottom.getIntrinsicHeight()));
            }
            contentLeft = left;
            contentTop = top;
            // 调用这个来绘制背景遮罩和阴影
            invalidate();

            if (scrollPercent >= 1) {
                // 滑动超过1则销毁 Activity
                if (!attachActivity.isFinishing()) {
                    attachActivity.finish();
                    // 不显示 Activity 切换动画
                    attachActivity.overridePendingTransition(0, 0);
                }
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            final int childWidth = releasedChild.getWidth();
            final int childHeight = releasedChild.getHeight();
            // 计算 ContentView 最终滑动目标 left 和 top
            // 这里说明下 xvel 和 yvel，他俩的取值范围为系统判断触摸滑动fling事件的最大和最小滑动速率，小于 mMinVelocity 统统返回0
            int left = 0, top = 0;
            if ((trackingEdge & EDGE_LEFT) != 0) {
                left = xvel > 0 || xvel == 0 && scrollPercent > scrollThreshold ? childWidth
                        + shadowLeft.getIntrinsicWidth() + OVER_SCROLL_DISTANCE : 0;
            } else if ((trackingEdge & EDGE_RIGHT) != 0) {
                left = xvel < 0 || xvel == 0 && scrollPercent > scrollThreshold ? -(childWidth
                        + shadowLeft.getIntrinsicWidth() + OVER_SCROLL_DISTANCE) : 0;
            } else if ((trackingEdge & EDGE_BOTTOM) != 0) {
                top = yvel < 0 || yvel == 0 && scrollPercent > scrollThreshold ? -(childHeight
                        + shadowBottom.getIntrinsicHeight() + OVER_SCROLL_DISTANCE) : 0;
            }
            // 让拖拽视图滑动到指定位置
            dragHelper.settleCapturedViewAt(left, top);
            invalidate();
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            if (state == ViewDragHelper.STATE_IDLE) {
                trackingEdge = EDGE_INVALID;
            }
        }
    }

    /**
     ================================== 外部调用 ==================================
     */

    /**
     * 设置控制滑动的 ContentView，也就是关联 Activity 的界面视图
     *
     * @param view
     */
    private void _setContentView(View view) {
        contentView = view;
    }

    /**
     * 设置关联的 Activity
     * 重要!必须调用
     *
     * @param activity
     */
    public void attachToActivity(Activity activity, @EdgeFlag int edgeFlag) {
        attachActivity = activity;
        TypedArray a = activity.getTheme().obtainStyledAttributes(new int[]{
                android.R.attr.windowBackground
        });
        int background = a.getResourceId(0, 0);
        a.recycle();

        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        decorChild.setBackgroundResource(background);
        decor.removeView(decorChild);
        addView(decorChild);
        _setContentView(decorChild);
        decor.addView(this);
        setEdgeFlag(edgeFlag);
    }

    public int getEdgeFlag() {
        return edgeFlag;
    }

    public void setEdgeFlag(@EdgeFlag int edgeFlag) {
        this.edgeFlag = edgeFlag;
        dragHelper.setEdgeTrackingEnabled(edgeFlag);
        if ((this.edgeFlag & EDGE_LEFT) != 0 && shadowLeft == null) {
            shadowLeft = ContextCompat.getDrawable(attachActivity, R.mipmap.ic_shadow_left);
        }
        if ((this.edgeFlag & EDGE_RIGHT) != 0 && shadowRight == null) {
            shadowRight = ContextCompat.getDrawable(attachActivity, R.mipmap.ic_shadow_right);
        }
        if ((this.edgeFlag & EDGE_BOTTOM) != 0 && shadowBottom == null) {
            shadowBottom = ContextCompat.getDrawable(attachActivity, R.mipmap.ic_shadow_bottom);
        }
        invalidate();
    }

    public float getScrollThreshold() {
        return scrollThreshold;
    }

    public void setScrollThreshold(float scrollThreshold) {
        this.scrollThreshold = scrollThreshold;
    }

    public boolean isEnableScroll() {
        return enableScroll;
    }

    public void setEnableScroll(boolean enableScroll) {
        this.enableScroll = enableScroll;
    }

    public void setShadowLeft(Drawable shadowLeft) {
        this.shadowLeft = shadowLeft;
    }

    public void setShadowRight(Drawable shadowRight) {
        this.shadowRight = shadowRight;
    }

    public void setShadowBottom(Drawable shadowBottom) {
        this.shadowBottom = shadowBottom;
    }

    public int getScrimColor() {
        return scrimColor;
    }

    public void setScrimColor(int scrimColor) {
        this.scrimColor = scrimColor;
    }

    /**
     * 设置可拖拽的边缘大小
     *
     * @param edgeSize
     */
    public void setEdgeSize(int edgeSize) {
        try {
            Field edgeSizeField = dragHelper.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            edgeSizeField.setInt(dragHelper, edgeSize);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * ==================================  ==================================
     */

    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.PARAMETER)
    @IntDef({EDGE_LEFT, EDGE_RIGHT, EDGE_BOTTOM, EDGE_ALL})
    public @interface EdgeFlag {
    }
}
