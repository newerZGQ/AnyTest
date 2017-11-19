package com.zgq.wokao;

import android.app.Application;

import com.zgq.wokao.injector.components.ApplicationComponent;
import com.zgq.wokao.injector.components.DaggerApplicationComponent;
import com.zgq.wokao.injector.modules.ApplicationModule;

public class RimApplication extends Application {

    private static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initInjector();
    }

    public static ApplicationComponent getAppComponent() {
        return applicationComponent;
    }

    private void initInjector() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    private void initConfig() {

    }

}
