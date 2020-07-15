package com.zgq.wokao.injector.components;

import com.zgq.wokao.injector.PerActivity;
import com.zgq.wokao.injector.modules.BaseModule;
import com.zgq.wokao.injector.modules.SettingsModule;
import com.zgq.wokao.module.settings.SettingsActivity;
import com.zgq.wokao.module.settings.SettingsFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {SettingsModule.class,BaseModule.class})
public interface SettingsComponent {
    void inject(SettingsActivity activity);
    void inject(SettingsFragment fragment);
}
