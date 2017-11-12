package com.zgq.rim.injector.components;

import android.content.Context;

import com.zgq.rim.injector.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    // provide
    Context getContext();
}
