package com.zgq.wokao.injector.modules;

import com.zgq.wokao.injector.PerActivity;
import com.zgq.wokao.module.maintab.MainTabContract;
import com.zgq.wokao.module.maintab.MainTabPresenter;
import com.zgq.wokao.module.maintab.homepage.HomepageFragment;
import com.zgq.wokao.module.maintab.homepage.HomepagePresenter;
import com.zgq.wokao.module.maintab.manager.ManagerFragment;
import com.zgq.wokao.module.maintab.manager.ManagerPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MainTabModule {
    public MainTabModule(){}

    @PerActivity
    @Provides
    public HomepageFragment provideHomepageFragment(){
        return new HomepageFragment();
    }

    @PerActivity
    @Provides
    public ManagerFragment provideManagerFragment(){
        return new ManagerFragment();
    }

    @PerActivity
    @Provides
    public MainTabContract.MainTabPresenter provideMainTabPresenter(MainTabPresenter presenter){
        return presenter;
    }

    @PerActivity
    @Provides
    public MainTabContract.HomepagePresenter provideHomepagePresenter(HomepagePresenter presenter){
        return presenter;
    }

    @PerActivity
    @Provides
    public MainTabContract.ManagerPresenter provideManagerPresenter(ManagerPresenter presenter){
        return presenter;
    }
}
