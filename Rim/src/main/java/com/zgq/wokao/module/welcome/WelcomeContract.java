package com.zgq.wokao.module.welcome;

import android.content.Context;

import com.zgq.wokao.module.IPresenter;
import com.zgq.wokao.module.IView;

public interface WelcomeContract {
    interface MainView extends IView<MainPresenter> {
        void goHomeActivity();
    }

    interface MainPresenter extends IPresenter<MainView> {
        void checkSample(Context context);
    }

    interface SplashView extends IView<SplashPresenter> {
        void setTip(String message);
    }

    interface SplashPresenter extends IPresenter<SplashView> {
        void selectTip(String[] tips);
    }
}
