package com.iqiyi.vr.assistant.injector.components;

import com.iqiyi.vr.assistant.injector.PerActivity;
import com.iqiyi.vr.assistant.injector.modules.HomeMainModule;
import com.iqiyi.vr.assistant.module.home.HomeActivity;

import dagger.Component;

/**
 * Created by wangyancong on 2017/8/25.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = HomeMainModule.class)
public interface HomeMainComponent {
    void inject(HomeActivity activity);
}
