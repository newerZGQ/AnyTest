package com.zgq.wokao.injector.modules;

import com.zgq.wokao.injector.PerActivity;
import com.zgq.wokao.module.parser.ParserContract;
import com.zgq.wokao.module.parser.ParserPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zgq on 2017/11/22.
 */

@Module
public class ParserModule {
    public ParserModule(){}

    @Provides
    @PerActivity
    public ParserContract.Presenter provideMainPresenter(){
        return new ParserPresenter();
    }

}
