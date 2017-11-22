package com.zgq.wokao.module.welcome;

import com.zgq.wokao.module.base.BasePresenter;
import com.zgq.wokao.repository.RimRepository;

import javax.inject.Inject;

public class SplashPresenter extends BasePresenter<WelcomeContract.SplashView> implements WelcomeContract.SplashPresenter {
    private static final String TAG = SplashPresenter.class.getSimpleName();
    private RimRepository repository;

    @Inject
    public SplashPresenter(RimRepository repository){
        this.repository = repository;
    }

    @Override
    public void selectTip(String[] tips) {
        int i = (int) (Math.random() * 5);
        view.setTip(tips[i]);
    }
}
