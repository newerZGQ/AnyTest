package com.iqiyi.vr.assistant.injector.components;

import com.iqiyi.vr.assistant.injector.PerActivity;
import com.iqiyi.vr.assistant.injector.modules.PayMainModule;
import com.iqiyi.vr.assistant.module.store.pay.AppPayActivity;

import dagger.Component;

/**
 * Created by wangyancong on 2017/9/6.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = PayMainModule.class)
public interface PayMainComponent {
    void inject(AppPayActivity activity);
}
