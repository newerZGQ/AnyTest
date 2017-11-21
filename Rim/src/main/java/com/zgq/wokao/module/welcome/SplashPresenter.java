package com.zgq.wokao.module.welcome;

import com.zgq.wokao.repository.RimRepository;

import javax.inject.Inject;

public class SplashPresenter implements WelcomeContract.SplashPresenter {
    private static final String TAG = SplashPresenter.class.getSimpleName();

    private WelcomeContract.SplashView view;
    private RimRepository repository;

    @Inject
    public SplashPresenter(RimRepository repository){
        this.repository = repository;
    }

    @Override
    public void takeView(WelcomeContract.SplashView view) {
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
