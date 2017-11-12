package com.iqiyi.vr.assistant.injector.modules;

import com.iqiyi.vr.assistant.injector.PerActivity;
import com.iqiyi.vr.assistant.module.base.IBasePresenter;
import com.iqiyi.vr.assistant.module.store.pay.AppPayActivity;
import com.iqiyi.vr.assistant.module.store.pay.PayPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wangyancong on 2017/9/8.
 */
@Module
public class PayMainModule {
    private final AppPayActivity view;
    private final long appId;

    public PayMainModule(AppPayActivity view, long appId) {
        this.view = view;
        this.appId = appId;
    }

    @PerActivity
    @Provides
    public IBasePresenter providePresenter() {
        return new PayPresenter(view, appId);
    }
}
