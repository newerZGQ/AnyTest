package com.zgq.wokao.injector.components;

import com.zgq.wokao.injector.PerActivity;
import com.zgq.wokao.injector.modules.BaseModule;
import com.zgq.wokao.injector.modules.MainTabModule;
import com.zgq.wokao.module.maintab.MainTabActivity;
import com.zgq.wokao.module.maintab.homepage.HomepageFragment;
import com.zgq.wokao.module.maintab.manager.ManagerFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {MainTabModule.class, BaseModule.class})
public interface MaintabComponent {
    void inject(MainTabActivity mainTabActivity);
    void inject(HomepageFragment homepageFragment);
    void inject(ManagerFragment managerFragment);
}
