package com.iqiyi.vr.assistant.injector.components;

import com.iqiyi.vr.assistant.injector.PerFragment;
import com.iqiyi.vr.assistant.injector.modules.StoreMainModule;
import com.iqiyi.vr.assistant.module.store.main.StoreFragment;

import dagger.Component;

/**
 * Created by wangyancong on 2017/8/31.
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = StoreMainModule.class)
public interface StoreMainComponent {
    void inject(StoreFragment fragment);
}
