package com.zgq.wokao.injector.components;

import com.zgq.wokao.injector.PerActivity;
import com.zgq.wokao.injector.modules.BaseModule;
import com.zgq.wokao.injector.modules.HomeModule;
import com.zgq.wokao.module.home.HomeActivity;
import com.zgq.wokao.module.home.PaperFragment;
import com.zgq.wokao.module.home.ScheduleFragment;

import dagger.Component;

@PerActivity
@Component (dependencies = ApplicationComponent.class, modules = {HomeModule.class, BaseModule.class})
public interface HomeComponent {
    void inject(HomeActivity homeActivity);
    void inject(ScheduleFragment scheduleFragment);
    void inject(PaperFragment paperFragment);
}
