package com.zgq.wokao.module.welcome;

import com.zgq.wokao.module.IPresenter;
import com.zgq.wokao.module.IView;

public interface WelcomeContract {
    interface MainView extends IView<MainPresenter> {

    }

    interface MainPresenter extends IPresenter<MainView> {

    }

    interface SplashView extends IView<SplashPresenter> {
        void setTip(String message);
    }

    interface SplashPresenter extends IPresenter<SplashView> {
        void selectTip(String[] tips);
    }
}
