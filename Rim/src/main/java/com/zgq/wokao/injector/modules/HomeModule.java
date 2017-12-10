package com.zgq.wokao.injector.modules;

import com.zgq.wokao.injector.PerActivity;
import com.zgq.wokao.module.home.HomeContract;
import com.zgq.wokao.module.home.HomePresenter;
import com.zgq.wokao.module.home.PaperFragment;
import com.zgq.wokao.module.home.PaperPresenter;
import com.zgq.wokao.module.home.ScheduleFragment;
import com.zgq.wokao.module.home.SchedulePresenter;
import com.zgq.wokao.repository.RimRepository;

import dagger.Module;
import dagger.Provides;

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
    public HomeContract.MainPresenter provideHomePresenter(HomePresenter presenter){
        return presenter;
    }

    @PerActivity
    @Provides
    public HomeContract.SchedulePresenter provideSchedulePresenter(SchedulePresenter presenter){
        return presenter;
    }

    @PerActivity
    @Provides
    public HomeContract.PaperPresenter providePaperPresenter(PaperPresenter presenter){
        return presenter;
    }
}
