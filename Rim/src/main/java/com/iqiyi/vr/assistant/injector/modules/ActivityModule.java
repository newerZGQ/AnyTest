package com.iqiyi.vr.assistant.injector.modules;

import android.app.Activity;

import com.iqiyi.vr.assistant.injector.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wangyancong on 2017/8/23.
 */
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
