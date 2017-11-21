package com.zgq.wokao.module.welcome;

import com.zgq.wokao.module.BasePresenter;
import com.zgq.wokao.module.BaseView;

/**
 * Created by zhangguoqiang on 2017/11/19.
 */

public interface WelcomeContract {
    interface MainView extends BaseView<MainPresenter> {

    }

    interface MainPresenter extends BasePresenter<MainView> {

    }

    interface SplashView extends BaseView<SplashPresenter>{
        void setTip(String message);
    }

    interface SplashPresenter extends BasePresenter<SplashView>{
        void selectTip(String[] tips);
    }
}
