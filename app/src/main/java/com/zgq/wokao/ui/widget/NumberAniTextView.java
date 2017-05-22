package com.zgq.wokao.ui.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by zgq on 2017/5/21.
 */

public class NumberAniTextView extends TextView {
    private Context context;
    public NumberAniTextView(Context context) {
        super(context);
    }

    public NumberAniTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberAniTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void runInt(int start, int end, int duration) {

        ValueAnimator valueAnimator = ValueAnimator.ofInt((int) start,
                (int) end);
        valueAnimator.setDuration(duration);

        valueAnimator
                .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        //设置瞬时的数据值到界面上
                        setText(valueAnimator.getAnimatedValue().toString());
                    }
                });
        valueAnimator.start();
    }

}
