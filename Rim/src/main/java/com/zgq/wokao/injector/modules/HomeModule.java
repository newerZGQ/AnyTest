package com.zgq.wokao.injector.modules;

import com.zgq.wokao.injector.PerActivity;
import com.zgq.wokao.module.home.HomeContract;
import com.zgq.wokao.module.home.HomePresenter;
import com.zgq.wokao.module.home.PaperFragment;
import com.zgq.wokao.module.home.PaperPresenter;
import com.zgq.wokao.module.home.ScheduleFragment;
import com.zgq.wokao.module.home.SchedulePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangguoqiang on 2017/11/21.
 */

@Module
public class HomeModule {
    public HomeModule(){}

    @PerActivity
    @Provides
    public ScheduleFragment provideScheduleFragment(){
        return new ScheduleFragment();
    }

    @PerActivity
    @Provides
    public PaperFragment providePaperFragment(){
        return new PaperFragment();
    }

    @PerActivity
    @Provides
    public HomeContract.MainPresenter provideHomePresenter(){
        return new HomePresenter();
    }

    @PerActivity
    @Provides
    public HomeContract.SchedulePresenter provideSchedulePresenter(){
        return new SchedulePresenter();
    }

    @PerActivity
    @Provides
    public HomeContract.PaperPresenter providePaperPresenter(){
        return new PaperPresenter();
    }
}
