package com.zgq.pulltomenu;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by zgq on 2017/2/21.
 */

public class PullToMenuView extends RelativeLayout {
    private static final float factor = 1.8f;
    public PullToMenuView(Context context) {
        super(context);
    }

    public PullToMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PullToMenuView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = widthSize;
        int height = heightSize;

        heightMode = MeasureSpec.AT_MOST;
        widthMode = MeasureSpec.EXACTLY;

        width = (int)(width*factor);

        setMeasuredDimension(width,height);
    }

}
