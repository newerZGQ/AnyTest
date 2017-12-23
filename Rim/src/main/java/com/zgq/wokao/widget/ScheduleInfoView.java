package com.zgq.wokao.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zgq.wokao.R;

public class ScheduleInfoView extends RelativeLayout {

    private View rootView;

    private int currentDailyCount;

    private AccuracyView accuracyCircle;
    private AccuracyView scheduleCircle;
    private NumberAniTextView accuracyText;
    private TextView todayNumText;
    private NumberAniTextView dailyCountText;

    private Context context;

    public ScheduleInfoView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ScheduleInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public ScheduleInfoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    private void init() {
        rootView = LayoutInflater.from(context).inflate(R.layout.scheduleinfoview, this);
        accuracyCircle = (AccuracyView) rootView.findViewById(R.id.accuracy_view);
        accuracyCircle.setProgressColor(getResources().getColor(R.color.color_accuracy_circle_1_hard));
        accuracyCircle.setProgressBackgroundColor(getResources().getColor(R.color.color_accuracy_circle_1_light));
        scheduleCircle = (AccuracyView) rootView.findViewById(R.id.schedule_view);
        scheduleCircle.setProgressColor(getResources().getColor(R.color.color_accuracy_circle_2_hard));
        scheduleCircle.setProgressBackgroundColor(getResources().getColor(R.color.color_accuracy_circle_2_light));
        accuracyText = (NumberAniTextView) rootView.findViewById(R.id.accuracy);
        todayNumText = (TextView) rootView.findViewById(R.id.today_num);
        dailyCountText = (NumberAniTextView) rootView.findViewById(R.id.daily_count);
    }


    public void updateWithAnimator(int accuracy, final String todayNum, final String dailyCount) {
        int todayNumI = Integer.valueOf(todayNum);
        int dailyCountI = Integer.valueOf(dailyCount);
        float scheduleF = 0f;
        if (dailyCountI != 0) {
            scheduleF = (float) todayNumI / (float) dailyCountI;
        }
        accuracyCircle.setProgress(((float) accuracy)/100);
        scheduleCircle.setProgress(scheduleF);

        accuracyText.runInt(0, accuracy, 200);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(todayNumText, "scaleX", 1f, 0.8f),
                ObjectAnimator.ofFloat(todayNumText, "scaleY", 1f, 0.8f),
                ObjectAnimator.ofFloat(todayNumText, "alpha", 1f, 0f),
                ObjectAnimator.ofFloat(dailyCountText, "scaleX", 1f, 0.8f),
                ObjectAnimator.ofFloat(dailyCountText, "scaleY", 1f, 0.8f),
                ObjectAnimator.ofFloat(dailyCountText, "alpha", 1f, 0f)
        );
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                todayNumText.setText(todayNum);
                dailyCountText.setText(dailyCount);
                AnimatorSet set_1 = new AnimatorSet();
                set_1.playTogether(
                        ObjectAnimator.ofFloat(todayNumText, "scaleX", 0.8f, 1f),
                        ObjectAnimator.ofFloat(todayNumText, "scaleY", 0.8f, 1f),
                        ObjectAnimator.ofFloat(todayNumText, "alpha", 0f, 1f),
                        ObjectAnimator.ofFloat(dailyCountText, "scaleX", 0.8f, 1f),
                        ObjectAnimator.ofFloat(dailyCountText, "scaleY", 0.8f, 1f),
                        ObjectAnimator.ofFloat(dailyCountText, "alpha", 0f, 1f)
                );
                set_1.setDuration(200).start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set.setDuration(200).start();
    }

    public void updateImmediate(int accuracy, final String todayNum, final String dailyCount){
        int todayNumI = Integer.valueOf(todayNum);
        int dailyCountI = Integer.valueOf(dailyCount);
        float scheduleF = 0f;
        if (dailyCountI != 0) {
            scheduleF = (float) todayNumI / (float) dailyCountI;
        }
        accuracyCircle.setProgress(((float) accuracy)/100);
        scheduleCircle.setProgress(scheduleF);
        accuracyText.setText(""+accuracy);
        todayNumText.setText(todayNum);
        dailyCountText.setText(dailyCount);
    }

    public void changDailyCount(int end, int duration) {
        if (duration == 0) duration = 200;
        dailyCountText.runInt(currentDailyCount, end, duration);
    }
}

