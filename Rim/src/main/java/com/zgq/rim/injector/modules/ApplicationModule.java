package com.zgq.rim.injector.modules;

import android.app.Application;
import android.content.Context;
import com.zgq.rim.RimApplication;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class ApplicationModule {
    //expose Application as an injectable context
    @Binds
    abstract Context bindContext(Application application);
}
