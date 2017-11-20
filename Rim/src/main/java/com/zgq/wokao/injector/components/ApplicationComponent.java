package com.zgq.wokao.injector.components;

import android.content.Context;

import com.zgq.wokao.injector.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Context getContext();
}
