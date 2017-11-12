package com.zgq.rim.injector.modules;

import android.content.Context;
import com.zgq.rim.RimApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final RimApplication application;

    public ApplicationModule(RimApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return application.getApplicationContext();
    }
}
