package com.zgq.wokao.injector.modules;

import android.app.Activity;

import com.zgq.wokao.injector.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @PerActivity
    @Provides
    Activity getActivity() {
        return activity;
    }
}
