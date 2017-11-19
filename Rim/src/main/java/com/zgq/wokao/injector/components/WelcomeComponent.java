package com.zgq.wokao.injector.components;

import com.zgq.wokao.injector.PerActivity;
import com.zgq.wokao.injector.modules.WelcomeModule;
import com.zgq.wokao.module.welcome.WelcomeActivity;

import dagger.Component;

/**
 * Created by zhangguoqiang on 2017/11/19.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = WelcomeModule.class)
public interface WelcomeComponent {
    void inject(WelcomeActivity activity);
}
