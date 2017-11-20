package com.zgq.wokao.injector.components;

import com.zgq.wokao.injector.PerActivity;
import com.zgq.wokao.injector.modules.BaseModule;
import com.zgq.wokao.injector.modules.WelcomeModule;
import com.zgq.wokao.module.welcome.WelcomeActivity;
import com.zgq.wokao.module.welcome.WelcomeFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {WelcomeModule.class, BaseModule.class})
public interface WelcomeComponent {
    void inject(WelcomeActivity activity);
    void inject(WelcomeFragment fragment);
}
