package com.zgqview.accuracy;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by zgq on 2017/3/19.
 */

public class ScheduleInfoView extends RelativeLayout {

    private View rootView;

    private LinearLayout topView;
    private TextView topAccuracy;
    private TextView topTodayNum;
    private TextView topDailyCount;

    private RelativeLayout btmView;
    private AccuracyView accuracyView;
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
        topAccuracy = (TextView) rootView.findViewById(R.id.top_accuracy);
        topTodayNum = (TextView) rootView.findViewById(R.id.top_today_num);
        topDailyCount = (TextView) rootView.findViewById(R.id.top_daily_count);

        btmView = (RelativeLayout) rootView.findViewById(R.id.btm_view);
        accuracyView = (AccuracyView) rootView.findViewById(R.id.accuracy_view);
        btmAccuracy = (TextView) rootView.findViewById(R.id.btm_accuracy);
        btmTodayNum = (TextView) rootView.findViewById(R.id.btm_today_num);
        btmDailyCount = (TextView) rootView.findViewById(R.id.btm_daily_count);

        viewAnimator(btmView,0f, 0f, 0f, 0f, 0);
        viewAnimator(topView,0f, 1f, 0f, 1f, 0);
    }

    public void setContent(String accuracy, float progress, String todayNum, String dailyCount){
        topTodayNum.setText(todayNum);
        topDailyCount.setText(dailyCount);

        btmTodayNum.setText(todayNum);
        btmDailyCount.setText(dailyCount);

        topAccuracy.setText(accuracy);
        btmAccuracy.setText(accuracy);

        accuracyView.setProgress(progress);
    }

    public void changeContent(final String accuracy,final String todayNum, final String dailyCount){
        switch (status){
            case BOTTOM:
                changeAnimator(accuracy,todayNum,dailyCount,btmAccuracy,btmTodayNum,btmDailyCount);
                break;
            case TOP:
                changeAnimator(accuracy,todayNum,dailyCount,topAccuracy,topTodayNum,topDailyCount);
                break;
            default:
                break;
        }
    }

    private void changeAnimator(final String accuracy,final String todayNum,
                                final String dailyCount, final TextView accuracyTv,
                                final TextView todayNumTv, final TextView dailyCountTv){
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(accuracyTv,"scaleX",1f,0f),
                ObjectAnimator.ofFloat(accuracyTv,"scaleY",1f,0f),
                ObjectAnimator.ofFloat(todayNumTv,"scaleX",1f,0f),
                ObjectAnimator.ofFloat(todayNumTv,"scaleY",1f,0f),
                ObjectAnimator.ofFloat(dailyCountTv,"scaleX",1f,0f),
                ObjectAnimator.ofFloat(dailyCountTv,"scaleY",1f,0f)
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
                setContent(accuracy,progress,todayNum,dailyCount);
                AnimatorSet set_1 = new AnimatorSet();
                set_1.playTogether(
                        ObjectAnimator.ofFloat(accuracyTv,"scaleX",0f,1f),
                        ObjectAnimator.ofFloat(accuracyTv,"scaleY",0f,1f),
                        ObjectAnimator.ofFloat(todayNumTv,"scaleX",0f,1f),
                        ObjectAnimator.ofFloat(todayNumTv,"scaleY",0f,1f),
                        ObjectAnimator.ofFloat(dailyCountTv,"scaleX",0f,1f),
                        ObjectAnimator.ofFloat(dailyCountTv,"scaleY",0f,1f)
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
