package com.zgq.wokao.injector.modules;

import android.content.Context;
import com.zgq.wokao.RimApplication;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final RimApplication application;
    public ApplicationModule(RimApplication rimApplication){
        application = rimApplication;
    }

    @Provides
    @Singleton
    Context provideApplicationContext(){
        return application.getApplicationContext();
    }
}
