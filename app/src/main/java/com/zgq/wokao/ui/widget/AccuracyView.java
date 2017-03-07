package com.zgq.wokao.ui.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zgq.wokao.R;

import org.w3c.dom.Text;

/**
 * Created by zgq on 2017/3/7.
 */

public class AccuracyView extends LinearLayout {
    private TextView correctNum_tv;
    private TextView correctTxt_tv;
    private TextView wrongNum_tv;
    private TextView wrongTxt_tv;
    private View slider;
    private View bar;

    private int accuracy = 0;
    private int barWidth = 0;

    private Context context;

    public AccuracyView(Context context) {
        super(context);
        this.context = context;
        initView(context);
    }

    public AccuracyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(context);
    }

    public AccuracyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context);
    }

    private void initView(Context context){
        View.inflate(context, R.layout.accurary_view,this);
        correctNum_tv = (TextView) findViewById(R.id.correct_num);
        correctTxt_tv = (TextView) findViewById(R.id.correct_txt);
        wrongNum_tv = (TextView) findViewById(R.id.wrong_num);
        wrongTxt_tv = (TextView) findViewById(R.id.wrong_txt);
        slider = findViewById(R.id.slider);
        bar = findViewById(R.id.slider_bar);
    }

    public void setCorrectNum(int num){
        correctNum_tv.setText(""+num);
    }

    public void setWrongNum(int num){
        wrongNum_tv.setText(""+num);
    }

    public void setAccuracy(int accuracy){
        this.accuracy = accuracy;
    }

    public void setNumTxtSize(int size){
        correctNum_tv.setTextSize(size);
        wrongNum_tv.setTextSize(size);
    }

    public void setLabelTxtSize(int size){
        correctTxt_tv.setTextSize(size);
        wrongTxt_tv.setTextSize(size);
    }

    public void setNumTxtColor(int color){
        correctNum_tv.setTextColor(context.getColor(color));
        wrongNum_tv.setTextColor(context.getColor(color));
    }

    public void setLabelTxtColor(int color){
        correctTxt_tv.setTextColor(context.getColor(color));
        wrongTxt_tv.setTextColor(context.getColor(color));
    }

    private void moveSlider(){
        barWidth = bar.getWidth();
        ObjectAnimator animator = ObjectAnimator.ofFloat(slider,"translationX",barWidth*accuracy/100);
        animator.setDuration(200);
        animator.start();
    }

}
