package com.zgq.wokao.module.welcome;

import javax.inject.Inject;

/**
 * Created by zhangguoqiang on 2017/11/19.
 */

public class WelcomePresenter implements WelcomeContract.Presenter {
    private WelcomeContract.View view;

    @Inject
    public WelcomePresenter(){}

    @Override
    public void takeView(WelcomeContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {

    }

    @Override
    public void loadTip() {
        view.setTip("this is a test");
    }
}
