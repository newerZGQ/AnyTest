package com.zgq.wokao.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.Util.DensityUtil;
import com.zgq.wokao.Util.DrawableUtil;

/**
 * Created by zgq on 16-7-17.
 */
public class QuestionOptionView extends LinearLayout {
    private TextView optionLabel;
    private TextView optionText;
    private Context context;
    public QuestionOptionView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public QuestionOptionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }
    private void init(){
        this.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,DensityUtil.dip2px(context,48f)));
        this.setOrientation(HORIZONTAL);

        int dimen = DensityUtil.dip2px(context,32f);
        optionLabel = new TextView(context);
        optionLabel.setTextSize(DensityUtil.dip2px(context,16f));
        optionLabel.setBackground(context.getResources().getDrawable(R.drawable.circle_background_upside_yellow));

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(dimen,dimen);
        params1.setMarginStart(DensityUtil.dip2px(context,16f));
        this.addView(optionLabel,params1);

        optionText = new TextView(context);
        optionText.setGravity(View.TEXT_ALIGNMENT_CENTER);
        optionText.setTextSize(DensityUtil.dip2px(context,18f));
        optionText.setText("55555");
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params2.setMarginStart(DensityUtil.dip2px(context,16f));
        this.addView(optionText,params2);
    }

    public void setContent(int position,String content){
        optionLabel.setText(String.valueOf((char)(position+65)));
        optionText.setText(content);
    }
    public void setNormalSignal(String text){

    }
    public void setTrueSignal(){

    }
    public void setFalseSignal(){

    }
}
