package com.zgq.wokao.injector.components;

import com.zgq.wokao.injector.PerActivity;
import com.zgq.wokao.injector.modules.BaseModule;
import com.zgq.wokao.injector.modules.ParserModule;
import com.zgq.wokao.module.parser.ParserActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ParserModule.class, BaseModule.class})
public interface ParserComponent {
    void inject(ParserActivity parserActivity);
}
