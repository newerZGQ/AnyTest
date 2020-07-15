package com.zgq.wokao.injector.components;

import com.zgq.wokao.injector.PerActivity;
import com.zgq.wokao.injector.modules.BaseModule;
import com.zgq.wokao.injector.modules.SearchModule;
import com.zgq.wokao.module.search.SearchActivity;
import com.zgq.wokao.module.search.SearchFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {SearchModule.class, BaseModule.class})
public interface SearchComponent {
    void inject(SearchActivity activity);
    void inject(SearchFragment fragment);
}
