package com.zgq.wokao.module.home;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;

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

        }

        @Override
        public void onshow() {

        }

        @Override
        public void onTaskSelected(int task) {

        }
    };

    @Override
    public void onClick(View view) {

    }


    public class SchedulePageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            presenter.updateDetail(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
