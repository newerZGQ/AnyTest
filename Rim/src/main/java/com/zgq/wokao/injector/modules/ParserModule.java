package com.zgq.wokao.injector.modules;

import com.zgq.wokao.injector.PerActivity;
import com.zgq.wokao.module.parser.ParserContract;
import com.zgq.wokao.module.parser.ParserPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ParserModule {
    public ParserModule(){}

    @Provides
    @PerActivity
    public ParserContract.Presenter provideMainPresenter(ParserPresenter presenter){
        return presenter;
    }

}
