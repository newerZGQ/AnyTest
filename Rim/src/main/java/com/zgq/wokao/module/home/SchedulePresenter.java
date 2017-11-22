package com.zgq.wokao.module.home;

import com.zgq.wokao.module.base.BasePresenter;
import com.zgq.wokao.repository.RimRepository;

import javax.inject.Inject;

public class SchedulePresenter extends BasePresenter<HomeContract.ScheduleView>
        implements HomeContract.SchedulePresenter {

    private RimRepository repository;

    @Inject
    public SchedulePresenter(RimRepository repository){
        this.repository = repository;
    }


    @Override
    public void loadSchedule() {

    }

    @Override
    public void updateDetail(int index) {

    }
}
