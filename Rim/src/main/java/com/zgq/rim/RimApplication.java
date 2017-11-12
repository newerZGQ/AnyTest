package com.zgq.rim;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.Logger;
import com.zgq.rim.injector.components.ApplicationComponent;
import com.zgq.rim.injector.components.DaggerApplicationComponent;
import com.zgq.rim.injector.modules.ApplicationModule;

public class RimApplication extends Application {

    private static ApplicationComponent sAppComponent;
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        _initInjector();
        _initConfig();
    }

    public static ApplicationComponent getAppComponent() {
        return sAppComponent;
    }

    public static Context getContext() {
        return sContext;
    }

    /**
     * 初始化注射器
     */
    private void _initInjector() {
        // 这里不做注入操作，只提供一些全局单例数据
        sAppComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    /**
     * 初始化配置
     */
    private void _initConfig() {

    }
}
