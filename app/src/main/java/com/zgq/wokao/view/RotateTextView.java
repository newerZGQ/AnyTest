package com.zgq.wokao.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.zgq.wokao.R;

/**
 * Created by zgq on 16-7-8.
 */
public class RotateTextView extends TextView implements ObjectAnimator.AnimatorListener{
    private static final int UPSIDE   = 1;
    private static final int DOWNSIDE = 2;
    private int currentSide = UPSIDE;
    private Context context;

    private UpAndDownSideStyle sidesStyle;

    public RotateTextView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public RotateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public RotateTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }
    private void init(){
        setUpside();
    }
    public void setSidesStyle(UpAndDownSideStyle sidesStyle){
        this.sidesStyle = sidesStyle;
    }
    private void setUpside(){
        this.setText("U");
        this.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

    }
    private void setDownside(){

    }

    public void changeSide(){
        ObjectAnimator up2downFirstStep = new ObjectAnimator().ofFloat(this,"rotationY",0,90f).setDuration(100);
        up2downFirstStep.addListener(this);
        up2downFirstStep.start();
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        switch (currentSide){
            case UPSIDE:
                sidesStyle.setDownSide();
                currentSide = DOWNSIDE;
                break;
            case DOWNSIDE:
                sidesStyle.setUpSide();
                currentSide = UPSIDE;
                break;
        }
        ObjectAnimator.ofFloat(this,"rotationY",270f,360f).setDuration(100).start();
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
    public interface UpAndDownSideStyle{
        public void setUpSide();
        public void setDownSide();
    }
}
