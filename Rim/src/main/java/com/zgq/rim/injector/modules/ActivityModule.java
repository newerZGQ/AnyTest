package com.zgq.rim.injector.modules;

import android.app.Activity;

import com.zgq.rim.injector.PerActivity;

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
