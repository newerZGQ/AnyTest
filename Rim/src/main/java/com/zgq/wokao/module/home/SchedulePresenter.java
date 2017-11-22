package com.zgq.wokao.module.home;

import com.zgq.wokao.repository.RimRepository;

public class SchedulePresenter implements HomeContract.SchedulePresenter {

    private RimRepository repository;

    private HomeContract.ScheduleView view;

    public SchedulePresenter(RimRepository repository){
        this.repository = repository;
    }

    @Override
    public void takeView(HomeContract.ScheduleView view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        view = null;
    }

    @Override
    public void subscribe() {
        loadSchedule();
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void loadSchedule() {

    }

    @Override
    public void updateDetail(int index) {

    }
}
