package com.zgq.wokao.injector.components;

import com.zgq.wokao.injector.PerActivity;
import com.zgq.wokao.injector.modules.BaseModule;
import com.zgq.wokao.injector.modules.HomeModule;
import com.zgq.wokao.module.home.HomeActivity;

import dagger.Component;

/**
 * Created by zhangguoqiang on 2017/11/21.
 */

@PerActivity
@Component (dependencies = ApplicationComponent.class, modules = {HomeModule.class, BaseModule.class})
public interface HomeComponent {
    void inject(HomeActivity homeActivity);
}
