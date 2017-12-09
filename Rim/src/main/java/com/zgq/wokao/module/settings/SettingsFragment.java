package com.zgq.wokao.module.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.Preference;
import android.view.View;

import com.zgq.wokao.R;
import com.zgq.wokao.injector.components.DaggerSettingsComponent;
import com.zgq.wokao.injector.modules.SettingsModule;
import com.zgq.wokao.module.base.BasePreferenceFragment;

public class SettingsFragment extends BasePreferenceFragment<SettingsContract.SettingsPresenter>
        implements SettingsContract.SettingsView {
    @Override
    protected void daggerInject() {
        DaggerSettingsComponent.builder()
                .applicationComponent(getAppComponent())
                .settingsModule(new SettingsModule())
                .build()
                .inject(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.xml.prefs_settings;
    }

    @Override
    protected void initViews() {
        findPreference("setting_to_learning").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                return true;
            }
        });

        findPreference("setting_to_share_app").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return true;
            }
        });
    }


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }
}
