package com.zgq.rim;

import com.zgq.rim.injector.components.AppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class RimApplication extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
        return null;
    }
}
