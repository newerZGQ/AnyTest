package com.zgq.wokao.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.util.DensityUtil;

/**
 * Created by zgq on 16-7-17.
 */
public class QuestionOptionView extends LinearLayout {
    public static int SELECTED = 2;
    public static int UNSELECTED = 1;

    private int currentMode = UNSELECTED;

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

    private void init() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, DensityUtil.dip2px(context, 8), 0, 0);
        this.setLayoutParams(layoutParams);
        this.setOrientation(HORIZONTAL);

        int dimen = DensityUtil.dip2px(context, 32f);
        optionLabel = new TextView(context);
        optionLabel.setGravity(Gravity.CENTER);
        optionLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        optionLabel.setBackground(context.getResources().getDrawable(R.drawable.circle_background_option_unselected));

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(dimen, dimen);
//        params1.setMarginStart(DensityUtil.dip2px(context,13f));
        params1.setMargins(DensityUtil.dip2px(context, 13f), DensityUtil.dip2px(context, 1f), 0, 0);
        this.addView(optionLabel, params1);

        optionText = new TextView(context);
        optionText.setGravity(View.TEXT_ALIGNMENT_CENTER);
        optionText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        optionText.setText("");
        optionText.setTextColor(context.getResources().getColor(R.color.colorBlack));
        optionText.setGravity(Gravity.START);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params2.setMarginStart(DensityUtil.dip2px(context, 16f));
        this.addView(optionText, params2);
    }

    public void setContent(String label, String content) {
        optionLabel.setText(label);
        optionText.setText(content);
    }

    public void setToCorrect() {
        optionLabel.setBackground(context.getResources().getDrawable(R.drawable.circle_background_option_select_correct));
        optionLabel.setTextColor(context.getResources().getColor(R.color.colorWhite));
        optionText.setTextColor(context.getResources().getColor(R.color.colorGreen));
    }

    public void setToWrong() {
        optionLabel.setBackground(context.getResources().getDrawable(R.drawable.circle_background_option_select_wrong));
        optionLabel.setTextColor(context.getResources().getColor(R.color.colorWhite));
    }

    public void setUnselected() {
        optionLabel.setBackground(context.getResources().getDrawable(R.drawable.circle_background_option_unselected));
        optionLabel.setTextColor(context.getResources().getColor(R.color.colorFirstLevelText));
        optionText.setTextColor(context.getResources().getColor(R.color.colorBlack));

        currentMode = UNSELECTED;
    }

    public void setSelected() {
        optionLabel.setBackground(context.getResources().getDrawable(R.drawable.circle_background_option_selected));
        optionLabel.setTextColor(context.getResources().getColor(R.color.colorWhite));
        optionText.setTextColor(context.getResources().getColor(R.color.colorBlue));

        currentMode = SELECTED;
    }

    public int getCurrentMode() {
        return currentMode;
    }
}
