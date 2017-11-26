package com.zgq.wokao.module.home;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;

import com.orhanobut.logger.Logger;
import com.zgq.wokao.R;
import com.zgq.wokao.adapter.SchedulePagerAdapter;
import com.zgq.wokao.entity.paper.info.ExamPaperInfo;
import com.zgq.wokao.entity.paper.info.Schedule;
import com.zgq.wokao.injector.components.DaggerHomeComponent;
import com.zgq.wokao.injector.modules.HomeModule;
import com.zgq.wokao.module.base.BaseFragment;
import com.zgq.wokao.widget.ScheduleInfoView;
import com.zgq.wokao.widget.TaskSettingLayout;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import butterknife.BindView;
import io.realm.RealmResults;

public class ScheduleFragment extends BaseFragment<HomeContract.SchedulePresenter>
        implements HomeContract.ScheduleView, View.OnClickListener {

    @BindView(R.id.schedule_pager)
    ViewPager paperInfoList;
    @BindView(R.id.schedule_info_view)
    ScheduleInfoView scheduleInfoView;
    @BindView(R.id.no_schedule_layout)
    ViewStub noContentStub;
    @BindView(R.id.schedule_info_layout)
    LinearLayout infoLayout;
    @BindView(R.id.task_setting_layout)
    TaskSettingLayout taskSettingLayout;

    private SchedulePagerAdapter schedulePagerAdapter;

    private Listener listener;

    private int viewPagerPos;

    @Override
    protected void daggerInject() {
        DaggerHomeComponent.builder()
                .applicationComponent(getAppComponent())
                .homeModule(new HomeModule())
                .build()
                .inject(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_schedule;
    }

    @Override
    protected void initViews() {
        schedulePagerAdapter = new SchedulePagerAdapter(new ArrayList<>(0), pagerItemListener);
        paperInfoList.setAdapter(schedulePagerAdapter);
        paperInfoList.addOnPageChangeListener(new SchedulePageChangeListener());
        scheduleInfoView.setOnClickListener(this);
        infoLayout.setOnClickListener(this);
        taskSettingLayout.setOnTaskSettingListener(taskSettingListener);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener){
            this.listener = (Listener) context;
        }
    }

    @Override
    public void setDetail(@Nonnull Schedule schedule) {
        int accuracy = (int) (schedule.getCorrectCount() * 1.0f / schedule.getTotalCount());
        scheduleInfoView.updateWithAnimator(accuracy*100,
                String.valueOf(schedule.getDailyRecords().last().getStudyCount())
                , String.valueOf(schedule.getDailyTask()));
    }

    @Override
    public void setSchedulePapers(RealmResults<ExamPaperInfo> examPaperInfos) {
        schedulePagerAdapter.replaceData(examPaperInfos);
    }

    @Override
    public void notifyDataChanged() {
        schedulePagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyView() {
        paperInfoList.setVisibility(View.GONE);
        scheduleInfoView.setVisibility(View.GONE);
        if (noContentStub == null) {
            noContentStub.inflate();
        } else {
            noContentStub.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideEmptyView() {
        paperInfoList.setVisibility(View.VISIBLE);
        scheduleInfoView.setVisibility(View.VISIBLE);
        if (noContentStub == null) {
            noContentStub.inflate();
        } else {
            noContentStub.setVisibility(View.GONE);
        }
    }

    public void updateView(){
        presenter.loadSchedules(false);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.schedule_info_view:
                break;
            case R.id.schedule_info_layout:
                startSettingDaily();
                break;
            default:
                break;
        }
    }

    private void startSettingDaily(){
        actionViewPagerAndScheduleView(true,300);
        taskSettingLayout.show();
    }

    private void actionViewPagerAndScheduleView(boolean toHide, int duration){
        float viewPagerStartPos;
        float viewPagerEndPos;
        float scheduleStartPos;
        float scheduleEndPos;
        float scheduleStartScale;
        float scheduleEndScale;
        if (toHide) {
            viewPagerStartPos = 0;
            viewPagerEndPos = paperInfoList.getHeight();
            scheduleStartPos = 0;
            scheduleEndPos = -scheduleInfoView.getHeight()/10;
            scheduleStartScale = 1;
            scheduleEndScale = 0.9f;
        }else{
            viewPagerStartPos = paperInfoList.getHeight();
            viewPagerEndPos = 0;
            scheduleStartPos = -scheduleInfoView.getHeight()/10;
            scheduleEndPos = 0;
            scheduleStartScale = 0.9f;
            scheduleEndScale = 1f;
        }
        ObjectAnimator hideViewPager = ObjectAnimator.ofFloat(paperInfoList, "translationY",
                viewPagerStartPos, viewPagerEndPos).setDuration(duration);
        hideViewPager.start();
        AnimatorSet hideSchedule = new AnimatorSet();
        ObjectAnimator moveSchedule = ObjectAnimator.ofFloat(scheduleInfoView, "translationY",
                scheduleStartPos,scheduleEndPos);
        ObjectAnimator scaleScheduleX = ObjectAnimator.ofFloat(scheduleInfoView, "scaleX",
                scheduleStartScale,scheduleEndScale);
        ObjectAnimator scaleScheduleY = ObjectAnimator.ofFloat(scheduleInfoView, "scaleY",
                scheduleStartScale,scheduleEndScale);
        hideSchedule.playTogether(moveSchedule, scaleScheduleX, scaleScheduleY);
        hideSchedule.setDuration(duration);
        hideSchedule.start();
    }

    SchedulePagerAdapter.OnClickListener pagerItemListener = new SchedulePagerAdapter.OnClickListener() {
        @Override
        public void onClickTopLayout(int position) {

        }

        @Override
        public void onClickStartBtn(int position, String paperId) {

        }
    };

    TaskSettingLayout.OnTaskSettingListener taskSettingListener = new TaskSettingLayout.OnTaskSettingListener() {
        @Override
        public void onHide() {
            actionViewPagerAndScheduleView(false,300);
            listener.onEndSettingDaily();
        }

        @Override
        public void onshow() {
            listener.onStartSettingDaily();
            Logger.d("seekbar height" + taskSettingLayout.findViewById(R.id.seekbar).getHeight());
        }

        @Override
        public void onTaskSelected(int task) {
            scheduleInfoView.changDailyCount(task, 200);
            actionViewPagerAndScheduleView(false,300);
            listener.onEndSettingDaily();
            presenter.updateTask(viewPagerPos, task);
        }
    };

    public class SchedulePageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            presenter.updateDetail(position);
            viewPagerPos = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    public interface Listener {
        void onStartSettingDaily();
        void onEndSettingDaily();
    }
}
