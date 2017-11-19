package com.zgq.wokao.injector.modules;

import com.zgq.wokao.module.home.HomeContract;
import com.zgq.wokao.module.welcome.WelcomeActivity;
import com.zgq.wokao.module.welcome.WelcomeContract;
import com.zgq.wokao.module.welcome.WelcomeFragment;
import com.zgq.wokao.module.welcome.WelcomePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangguoqiang on 2017/11/19.
 */
@Module
public class WelcomeModule {
    public WelcomeModule(){}

    @Provides
    public WelcomeContract.Presenter providePresenter(WelcomePresenter presenter){
        return presenter;
    }

    @Provides
    public WelcomeFragment provideFragment(){
        return new WelcomeFragment();
    }

}
