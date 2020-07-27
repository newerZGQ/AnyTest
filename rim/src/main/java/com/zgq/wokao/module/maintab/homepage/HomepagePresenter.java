package com.zgq.wokao.module.maintab.homepage;

import com.zgq.wokao.module.base.BasePresenter;
import com.zgq.wokao.module.maintab.MainTabContract;
import com.zgq.wokao.repository.RimRepository;

import javax.inject.Inject;

public class HomepagePresenter extends BasePresenter<MainTabContract.HomepageView> implements MainTabContract.HomepagePresenter {

    private RimRepository repository;

    @Inject
    public HomepagePresenter(RimRepository repository){
        this.repository = repository;
    }
    @Override
    public void subscribe() {
        super.subscribe();
        view.updatePaperInfo("tetet", "tere");
    }

    private void update(String id) {

    }

    @Override
    public void switchPaper() {

    }
}
