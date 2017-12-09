package com.zgq.wokao.injector.modules;

import android.content.Context;

import com.google.common.eventbus.EventBus;
import com.zgq.wokao.RimContextCompat;
import com.zgq.wokao.injector.PerActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class BaseModule {

    @Provides
    public RimContextCompat provideRimContextCompat(Context context){
        RimContextCompat contextCompat = new RimContextCompat();
        contextCompat.config(context);
        return contextCompat;
    }
}
