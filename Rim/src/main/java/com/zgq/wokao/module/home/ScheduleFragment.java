package com.zgq.wokao.module.home;

import com.zgq.wokao.R;
import com.zgq.wokao.entity.paper.info.ExamPaperInfo;
import com.zgq.wokao.entity.summary.StudySummary;
import com.zgq.wokao.injector.components.DaggerHomeComponent;
import com.zgq.wokao.injector.modules.HomeModule;
import com.zgq.wokao.module.base.BaseFragment;

import io.realm.RealmList;

public class ScheduleFragment extends BaseFragment<HomeContract.SchedulePresenter> implements HomeContract.ScheduleView {
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

    }

    @Override
    public void setDetail() {

    }

    @Override
    public void setSchedulePaper(RealmList<ExamPaperInfo> examPaperInfos) {

    }
}
