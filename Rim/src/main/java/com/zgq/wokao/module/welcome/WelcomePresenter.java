package com.zgq.wokao.module.welcome;

import com.zgq.wokao.repository.RimRepository;

import javax.inject.Inject;

public class WelcomePresenter implements WelcomeContract.Presenter {
    private static final String TAG = WelcomePresenter.class.getSimpleName();

    private WelcomeContract.View view;
    private RimRepository repository;

    @Inject
    public WelcomePresenter(RimRepository repository){
        this.repository = repository;
    }

    @Override
    public void takeView(WelcomeContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        view = null;
    }

    @Override
    public void selectTip(String[] tips) {
        int i = (int) (Math.random() * 5);
        view.setTip(tips[i]);
    }
}
