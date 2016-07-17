package com.zgq.wokao.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by zgq on 16-7-17.
 */
public class TFQuestionOptionView extends TextView {
    public TFQuestionOptionView(Context context) {
        super(context);
    }

    public TFQuestionOptionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setNormalSignal(String text){
        this.setText(text);
    }
    public void setTrueSignal(){

    }
    public void setFalseSignal(){

    }
}
