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
public class RotateTextView extends TextView {
    private static final int UPSIDE   = 1;
    private static final int DOWNSIDE = 2;
    private int currentSide = UPSIDE;
    private Context context;
    public RotateTextView(Context context) {
        super(context);
        init();
    }

    public RotateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RotateTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        setUpside();
    }
    private void setUpside(){
        this.setText("U");
        this.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
    }
    private void setDownside(){
        this.setText("D");
        this.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
    }

    private void changeSide(){
        switch (currentSide){
            case UPSIDE:
                ObjectAnimator up2downFirstStep = new ObjectAnimator().ofFloat(this,"rotationY",0,90f);
                up2downFirstStep.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ObjectAnimator up2downSecodStep = new ObjectAnimator().ofFloat(RotateTextView.this,"rotationY",90f,180f);
                        up2downSecodStep.start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

        }

    }
}
