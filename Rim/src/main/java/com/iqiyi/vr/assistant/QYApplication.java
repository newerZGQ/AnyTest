package com.iqiyi.vr.assistant;

import android.app.Application;
import android.content.Context;

import com.iqiyi.vr.assistant.BuildConfig;
import com.iqiyi.vr.assistant.api.RetrofitService;
import com.iqiyi.vr.assistant.injector.components.ApplicationComponent;
import com.iqiyi.vr.assistant.injector.components.DaggerApplicationComponent;
import com.iqiyi.vr.assistant.injector.modules.ApplicationModule;
import com.orhanobut.logger.Logger;

/**
 * Created by wangyancong on 2016/11/11.
 * <p>
 * 应用Application
 */
public class QYApplication extends Application {

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
        if (BuildConfig.DEBUG) {
            Logger.init("Assist");
        }
        RetrofitService.init();
    }
}
