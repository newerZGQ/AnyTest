package com.zgq.wokao.injector.components;

import com.zgq.wokao.injector.PerActivity;
import com.zgq.wokao.injector.modules.BaseModule;
import com.zgq.wokao.injector.modules.WelcomeModule;
import com.zgq.wokao.module.welcome.SplashFragment;
import com.zgq.wokao.module.welcome.WelcomeActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {WelcomeModule.class, BaseModule.class})
public interface WelcomeComponent {
    void inject(WelcomeActivity activity);
    void inject(SplashFragment fragment);
}
