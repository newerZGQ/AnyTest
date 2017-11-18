package com.zgq.wokaofree.ui.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by zgq on 16-7-8.
 */
public class RotateTextView extends TextView implements ObjectAnimator.AnimatorListener {
    public static final int UPSIDE = 1;
    public static final int DOWNSIDE = 2;
    private int currentSide = UPSIDE;
    private Context context;

    private UpAndDownSideStyle sidesStyle;

    public RotateTextView(Context context) {
        super(context);
        this.context = context;
    }

    public RotateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public RotateTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    private void init() {
    }

    public void setSidesStyle(UpAndDownSideStyle sidesStyle) {
        this.sidesStyle = sidesStyle;
    }

    public void setUpside() {
        sidesStyle.setUpSideStyle();

    }

    public void setDownside() {
        sidesStyle.setDownSideStyle();
    }

    public int getCurrentSide() {
        return currentSide;
    }

    public void setCurrentSide(int currentSide) {
        this.currentSide = currentSide;
    }

    public void changeSide() {
        switch (currentSide) {
            case UPSIDE:
                currentSide = DOWNSIDE;
                break;
            case DOWNSIDE:
                currentSide = UPSIDE;
                break;
        }
        ObjectAnimator up2downFirstStep = new ObjectAnimator().ofFloat(this, "rotationY", 0, 90f).setDuration(100);
        up2downFirstStep.addListener(this);
        up2downFirstStep.start();
    }

    public void setToUpside() {
        currentSide = UPSIDE;
        startAnimation();
    }

    public void setToDownside() {
        currentSide = DOWNSIDE;
        startAnimation();
    }

    public void startAnimation() {
        ObjectAnimator up2downFirstStep = new ObjectAnimator().ofFloat(this, "rotationY", 0, 90f).setDuration(100);
        up2downFirstStep.addListener(this);
        up2downFirstStep.start();
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        switch (currentSide) {
            case DOWNSIDE:
                sidesStyle.setDownSideStyle();
                break;
            case UPSIDE:
                sidesStyle.setUpSideStyle();
                break;
        }
        ObjectAnimator.ofFloat(this, "rotationY", 270f, 360f).setDuration(100).start();
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    public interface UpAndDownSideStyle {
        public void setUpSideStyle();

        public void setDownSideStyle();
    }
}
