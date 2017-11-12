package com.iqiyi.vr.assistant.injector.components;

import com.iqiyi.vr.assistant.injector.PerActivity;
import com.iqiyi.vr.assistant.injector.modules.DetailMainModule;
import com.iqiyi.vr.assistant.module.store.detail.AppDetailActivity;

import dagger.Component;

/**
 * Created by wangyancong on 2017/9/2.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = DetailMainModule.class)
public interface DetailMainComponent {
    void inject(AppDetailActivity activity);
}
