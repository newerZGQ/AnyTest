package com.zgq.wokao.module.welcome;

import javax.inject.Inject;

/**
 * Created by zhangguoqiang on 2017/11/21.
 */

public class WelcomePresenter implements WelcomeContract.MainPresenter {

    @Inject
    public WelcomePresenter (){}

    @Override
    public void takeView(WelcomeContract.MainView view) {

    }

    @Override
    public void dropView() {

    }
}
