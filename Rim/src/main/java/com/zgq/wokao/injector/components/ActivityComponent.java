package com.zgq.wokao.injector.components;

import android.app.Activity;

import com.zgq.wokao.injector.PerActivity;
import com.zgq.wokao.injector.modules.ActivityModule;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();
}
