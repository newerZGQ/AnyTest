package com.zgq.wokao.injector.modules;

import android.content.Context;

import com.zgq.wokao.RimContextCompat;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangguoqiang on 2017/11/20.
 */

@Module
public class BaseModule {

    @Provides
    public RimContextCompat provideRimContextCompat(Context context){
        RimContextCompat contextCompat = new RimContextCompat();
        contextCompat.config(context);
        return contextCompat;
    }
}
