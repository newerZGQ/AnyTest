package com.zgq.wokao.injector.modules;

import com.zgq.wokao.injector.PerActivity;
import com.zgq.wokao.module.settings.SettingsContract;
import com.zgq.wokao.module.settings.SettingsFragment;
import com.zgq.wokao.module.settings.SettingsMainPresenter;
import com.zgq.wokao.module.settings.SettingsPresenter;
import com.zgq.wokao.module.settings.TipsFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class SettingsModule {
    public SettingsModule(){}

    @Provides
    @PerActivity
    public SettingsContract.SettingsPresenter providesSettingsPresenter(SettingsPresenter settingsPresenter){
        return settingsPresenter;
    }

    @Provides
    @PerActivity
    public SettingsContract.MainPresenter providesSettingsMainPresenter(SettingsMainPresenter mainPresenter){
        return mainPresenter;
    }

    @Provides
    @PerActivity
    public SettingsFragment providesSettingsFragment(){
        return new SettingsFragment();
    }

    @Provides
    @PerActivity
    public TipsFragment providesTipsFragment(){
        return new TipsFragment();
    }
}
