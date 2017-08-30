package com.zgq.wokao.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zgq.wokao.R;

/**
 * Created by zgq on 2017/3/19.
 */

public class ScheduleInfoView extends RelativeLayout {

    private View rootView;

    private int currentDailyCount;

    private LinearLayout topView;
    private TextView topIsCompletedTv;

    private RelativeLayout btmView;
    private AccuracyView accuracyView;
    private AccuracyView scheduleView;
    private NumberAniTextView btmAccuracy;
    private TextView btmTodayNum;
    private NumberAniTextView btmDailyCount;

    private Context context;

    private Status status = Status.TOP;


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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    private void init(){
        rootView = LayoutInflater.from(context).inflate(R.layout.scheduleinfoview,this);
        topView = (LinearLayout) rootView.findViewById(R.id.top_view);
        topIsCompletedTv = (TextView) rootView.findViewById(R.id.is_completed_day_count);

        btmView = (RelativeLayout) rootView.findViewById(R.id.btm_view);
        accuracyView = (AccuracyView) rootView.findViewById(R.id.accuracy_view);
        accuracyView.setProgressColor(getResources().getColor(R.color.color_accuracy_circle_1_hard));
        accuracyView.setProgressBackgroundColor(getResources().getColor(R.color.color_accuracy_circle_1_light));
        scheduleView = (AccuracyView) rootView.findViewById(R.id.schedule_view);
        scheduleView.setProgressColor(getResources().getColor(R.color.color_accuracy_circle_2_hard));
        scheduleView.setProgressBackgroundColor(getResources().getColor(R.color.color_accuracy_circle_2_light));
        btmAccuracy = (NumberAniTextView) rootView.findViewById(R.id.btm_accuracy);
        btmTodayNum = (TextView) rootView.findViewById(R.id.btm_today_num);
        btmDailyCount = (NumberAniTextView) rootView.findViewById(R.id.btm_daily_count);

        viewAnimator(btmView,0f, 0f, 0f, 0f, 0);
        viewAnimator(topView,0f, 1f, 0f, 1f, 0);
    }


    public void changeContent(final String accuracy,final String todayNum, final String dailyCount){
        switch (status){
            case BOTTOM:
                changeBtmAnimator(accuracy,todayNum,dailyCount,btmAccuracy,btmTodayNum,btmDailyCount);
                break;
            case TOP:
                changeTopAnimator(todayNum,dailyCount, topIsCompletedTv);
                break;
            default:
                break;
        }
    }

    public void changDailyCount(int end, int duration){
        if (duration == 0) duration = 200;
        btmDailyCount.runInt(currentDailyCount,end,duration);
    }

    private void changeTopAnimator(final String todayNum, final String dailyCount,
                                   final TextView topIsCompletedTv){
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(topIsCompletedTv,"scaleX",1f,0.8f),
                ObjectAnimator.ofFloat(topIsCompletedTv,"scaleY",1f,0.8f),
                ObjectAnimator.ofFloat(topIsCompletedTv,"alpha",1f,0f)
        );
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                int todayNumInt = Integer.valueOf(todayNum);
                int dailyCountInt = Integer.valueOf(dailyCount);
                if (todayNumInt < dailyCountInt){
                    topIsCompletedTv.setText("今日任务未完成，再接再厉！");
                    topIsCompletedTv.setTextColor(context.getResources().getColor(R.color.color_daily_count_uncompleted));
                }else{
                    topIsCompletedTv.setText("恭喜您！今日任务已经完成");
                    topIsCompletedTv.setTextColor(context.getResources().getColor(R.color.color_daily_count_completed));
                }
                //setTopContent(studyDayCount);
                AnimatorSet set_1 = new AnimatorSet();
                set_1.playTogether(
                        ObjectAnimator.ofFloat(topIsCompletedTv,"scaleX",0.8f,1f),
                        ObjectAnimator.ofFloat(topIsCompletedTv,"scaleY",0.8f,1f),
                        ObjectAnimator.ofFloat(topIsCompletedTv,"alpha",0f,1f)
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

    private void changeBtmAnimator(final String accuracy, final String todayNum,
                                   final String dailyCount, final NumberAniTextView accuracyTv,
                                   final TextView todayNumTv, final NumberAniTextView dailyCountTv){
        final float accuracyF = Float.valueOf(accuracy);
        int todayNumI = Integer.valueOf(todayNum);
        int dailyCountI = Integer.valueOf(dailyCount);
        float scheduleF = 0f;
        if (dailyCountI != 0){
            scheduleF = (float) todayNumI / (float) dailyCountI;
        }
        accuracyView.setProgress(accuracyF);
        scheduleView.setProgress(scheduleF);

        accuracyTv.runInt(0,(int)(accuracyF * 100),200);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(
//                ObjectAnimator.ofFloat(accuracyTv,"scaleX",1f,0.8f),
//                ObjectAnimator.ofFloat(accuracyTv,"scaleY",1f,0.8f),
//                ObjectAnimator.ofFloat(accuracyTv,"alpha",1f,0f),
                ObjectAnimator.ofFloat(todayNumTv,"scaleX",1f,0.8f),
                ObjectAnimator.ofFloat(todayNumTv,"scaleY",1f,0.8f),
                ObjectAnimator.ofFloat(todayNumTv,"alpha",1f,0f),
                ObjectAnimator.ofFloat(dailyCountTv,"scaleX",1f,0.8f),
                ObjectAnimator.ofFloat(dailyCountTv,"scaleY",1f,0.8f),
                ObjectAnimator.ofFloat(dailyCountTv,"alpha",1f,0f)
        );
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
//                accuracyTv.setText(""+ (int)(accuracyF * 100));
                todayNumTv.setText(todayNum);
                dailyCountTv.setText(dailyCount);
//                setBtmContent(accuracy,progress,todayNum,dailyCount);
                AnimatorSet set_1 = new AnimatorSet();
                set_1.playTogether(
//                        ObjectAnimator.ofFloat(accuracyTv,"scaleX",0.8f,1f),
//                        ObjectAnimator.ofFloat(accuracyTv,"scaleY",0.8f,1f),
//                        ObjectAnimator.ofFloat(accuracyTv,"alpha",0f,1f),
                        ObjectAnimator.ofFloat(todayNumTv,"scaleX",0.8f,1f),
                        ObjectAnimator.ofFloat(todayNumTv,"scaleY",0.8f,1f),
                        ObjectAnimator.ofFloat(todayNumTv,"alpha",0f,1f),
                        ObjectAnimator.ofFloat(dailyCountTv,"scaleX",0.8f,1f),
                        ObjectAnimator.ofFloat(dailyCountTv,"scaleY",0.8f,1f),
                        ObjectAnimator.ofFloat(dailyCountTv,"alpha",0f,1f)
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

    public void showBottom(int duration){
        viewAnimator(btmView,1f, 1f, 0f, 1f, duration);
        viewAnimator(topView,1f, 0f, 1f, 0f, duration);
        status = Status.BOTTOM;
    }

    public void showTop(int duration){
        viewAnimator(btmView,1f, 0f, 1f, 0f, duration);
        viewAnimator(topView,1f, 1f, 0f, 1f, duration);
        status = Status.TOP;
    }

    private void viewAnimator(View view, float startScale, float endScale,
                              float startAlpha, float endAlpha, int duration){
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(view,"scaleX",startScale,endScale),
                ObjectAnimator.ofFloat(view,"scaleY",startScale,endScale),
                ObjectAnimator.ofFloat(view,"alpha",startAlpha,endAlpha)
        );
        set.setDuration(duration).start();
    }


    public enum Status{
        TOP,BOTTOM;
    }
}

