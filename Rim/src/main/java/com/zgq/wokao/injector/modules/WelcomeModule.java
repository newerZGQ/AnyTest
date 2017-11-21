package com.zgq.wokao.injector.modules;

import com.zgq.wokao.injector.PerActivity;
import com.zgq.wokao.module.welcome.SplashFragment;
import com.zgq.wokao.module.welcome.WelcomeContract;
import com.zgq.wokao.module.welcome.SplashPresenter;
import com.zgq.wokao.module.welcome.WelcomePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangguoqiang on 2017/11/19.
 */
@Module
public class WelcomeModule {
    public WelcomeModule(){}

    @PerActivity
    @Provides
    public WelcomeContract.SplashPresenter provideSplashPresenter(SplashPresenter presenter){
        return presenter;
    }

    @PerActivity
    @Provides
    public WelcomeContract.MainPresenter provideWelcomePresenter(WelcomePresenter presenter){
        return presenter;
    }

    @PerActivity
    @Provides
    public SplashFragment provideFragment(){
        return new SplashFragment();
    }

}
