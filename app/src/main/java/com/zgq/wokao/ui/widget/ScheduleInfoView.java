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

    private LinearLayout topView;
    private TextView topIsCompletedTv;

    private RelativeLayout btmView;
    private AccuracyView accuracyView;
    private AccuracyView scheduleView;
    private TextView btmAccuracy;
    private TextView btmTodayNum;
    private TextView btmDailyCount;

    private boolean isExpanded = true;

    private float progress = 0;

    private Context context;

    private ObjectAnimator accuracyAnimator;

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
        scheduleView = (AccuracyView) rootView.findViewById(R.id.schedule_view);
        btmAccuracy = (TextView) rootView.findViewById(R.id.btm_accuracy);
        btmTodayNum = (TextView) rootView.findViewById(R.id.btm_today_num);
        btmDailyCount = (TextView) rootView.findViewById(R.id.btm_daily_count);

        viewAnimator(btmView,0f, 0f, 0f, 0f, 0);
        viewAnimator(topView,0f, 1f, 0f, 1f, 0);
    }

    public void setBtmContent(String accuracy, float progress, String todayNum, String dailyCount){
        btmTodayNum.setText(todayNum);
        btmDailyCount.setText(dailyCount);
        btmAccuracy.setText(accuracy);
        accuracyView.setProgress(progress);
    }

//    public void setTopContent(final String todayNum, final String dailyCount){
//        topIsCompletedTv.setText(studyDayCount);
//    }

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
                                   final String dailyCount, final TextView accuracyTv,
                                   final TextView todayNumTv, final TextView dailyCountTv){
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(accuracyTv,"scaleX",1f,0.8f),
                ObjectAnimator.ofFloat(accuracyTv,"scaleY",1f,0.8f),
                ObjectAnimator.ofFloat(accuracyTv,"alpha",1f,0f),
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
                accuracyTv.setText(accuracy);
                todayNumTv.setText(todayNum);
                dailyCountTv.setText(dailyCount);
                setBtmContent(accuracy,progress,todayNum,dailyCount);
                AnimatorSet set_1 = new AnimatorSet();
                set_1.playTogether(
                        ObjectAnimator.ofFloat(accuracyTv,"scaleX",0.8f,1f),
                        ObjectAnimator.ofFloat(accuracyTv,"scaleY",0.8f,1f),
                        ObjectAnimator.ofFloat(accuracyTv,"alpha",0f,1f),
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

    public void showBottom(){
        viewAnimator(btmView,1f, 1f, 0f, 1f, 500);
        viewAnimator(topView,1f, 0f, 1f, 0f, 500);
        status = Status.BOTTOM;
    }

    public void showTop(){
        viewAnimator(btmView,1f, 0f, 1f, 0f, 500);
        viewAnimator(topView,1f, 1f, 0f, 1f, 500);
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

    /**
     * Animate.
     *
     * @param progressBar the progress bar
     * @param listener    the listener
     */
    private void animate(final AccuracyView progressBar,
                         final Animator.AnimatorListener listener) {
        final float progress = (float) (Math.random() * 2);
        int duration = 3000;
        animate(progressBar, listener, progress, duration);
    }

    private void animate(final AccuracyView mAccuracyView, final Animator.AnimatorListener listener,
                         final float progress, final int duration) {

        accuracyAnimator = ObjectAnimator.ofFloat(mAccuracyView, "progress", progress);
        accuracyAnimator.setDuration(duration);

        accuracyAnimator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationCancel(final Animator animation) {
            }

            @Override
            public void onAnimationEnd(final Animator animation) {
                mAccuracyView.setProgress(progress);
            }

            @Override
            public void onAnimationRepeat(final Animator animation) {
            }

            @Override
            public void onAnimationStart(final Animator animation) {
            }
        });
        if (listener != null) {
            accuracyAnimator.addListener(listener);
        }
        accuracyAnimator.reverse();
        accuracyAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                mAccuracyView.setProgress((Float) animation.getAnimatedValue());
            }
        });
        mAccuracyView.setMarkerProgress(progress);
        accuracyAnimator.start();
    }

    public enum Status{
        TOP,BOTTOM;
    }
}

