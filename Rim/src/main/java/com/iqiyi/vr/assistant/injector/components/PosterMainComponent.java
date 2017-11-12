package com.iqiyi.vr.assistant.injector.components;

import com.iqiyi.vr.assistant.injector.PerActivity;
import com.iqiyi.vr.assistant.injector.modules.PosterMainModule;
import com.iqiyi.vr.assistant.module.store.poster.BigPosterActivity;

import dagger.Component;

/**
 * Created by wangyancong on 2017/9/6.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = PosterMainModule.class)
public interface PosterMainComponent {
    void inject(BigPosterActivity activity);
}
