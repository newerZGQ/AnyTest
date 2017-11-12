package com.iqiyi.vr.assistant.injector.components;

import android.app.Activity;

import com.iqiyi.vr.assistant.injector.PerActivity;
import com.iqiyi.vr.assistant.injector.modules.ActivityModule;

import dagger.Component;

/**
 * Created by wangyancong on 2017/8/23.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();
}
