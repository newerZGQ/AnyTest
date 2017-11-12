package com.zgq.rim.widget;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.zgq.rim.R;
import com.zgq.rim.util.AnimateHelper;

public class PullScrollView extends NestedScrollView {

    private View footView;
    private OnPullListener pullListener;
    private boolean isPullStatus = false;
    private float lastY;
    private int pullCriticalDistance;

    public PullScrollView(Context context) {
        this(context, null);
    }

    public PullScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        pullCriticalDistance = getResources().getDimensionPixelSize(R.dimen.pull_critical_distance);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (t >= (getChildAt(0).getMeasuredHeight() - getHeight()) && pullListener != null) {
            pullListener.isDoPull();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (MotionEventCompat.getActionMasked(ev)) {
            case MotionEvent.ACTION_MOVE:
                if (!isPullStatus) {
                    if (getScrollY() >= (getChildAt(0).getMeasuredHeight() - getHeight()) || getChildAt(0).getMeasuredHeight() < getHeight()) {
                        if (pullListener != null && pullListener.isDoPull()) {
                            isPullStatus = true;
                            lastY = ev.getY();
                        }
                    }
                } else if (lastY < ev.getY()) {
                    isPullStatus = false;
                    _pullFootView(0);
                } else {
                    float offsetY = lastY - ev.getY();
                    _pullFootView(offsetY);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (isPullStatus) {
                    if (footView.getHeight() > pullCriticalDistance && pullListener != null) {
                        if (!pullListener.handlePull()) {
                            AnimateHelper.doClipViewHeight(footView, footView.getHeight(), 0, 200);
                        }
                    } else {
                        AnimateHelper.doClipViewHeight(footView, footView.getHeight(), 0, 200);
                    }
                    isPullStatus = false;
                }
                break;
        }
        return super.onTouchEvent(ev);
    }


    public void setFootView(View footView) {
        this.footView = footView;
    }

    public void setPullListener(OnPullListener pullListener) {
        this.pullListener = pullListener;
    }

    private void _pullFootView(float offsetY) {
        if (footView != null) {
            ViewGroup.LayoutParams layoutParams = footView.getLayoutParams();
            layoutParams.height = (int) (offsetY * 1 / 2);
            footView.setLayoutParams(layoutParams);
        }
    }

    public interface OnPullListener {
        boolean isDoPull();

        boolean handlePull();
    }
}
