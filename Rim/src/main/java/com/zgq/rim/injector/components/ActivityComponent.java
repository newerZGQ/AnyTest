package com.zgq.rim.injector.components;

import android.app.Activity;

import com.zgq.rim.injector.PerActivity;
import com.zgq.rim.injector.modules.ActivityModule;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();
}
