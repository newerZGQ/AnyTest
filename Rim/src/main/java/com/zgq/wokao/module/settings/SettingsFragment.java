package com.zgq.wokao.module.settings;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
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
    private SettingsFragmentListener mListener;

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
                mListener.onLearingsClicked();
                return true;
            }
        });

        findPreference("setting_to_share_app").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                mListener.onShareAppClicked();
                return true;
            }
        });
    }


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SettingsFragmentListener) {
            mListener = (SettingsFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PaperFragmentListener");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (activity instanceof SettingsFragmentListener) {
                mListener = (SettingsFragmentListener) activity;
            } else {
                throw new RuntimeException(activity.toString()
                        + " must implement ABC_Listener");
            }
        }
    }

    public interface SettingsFragmentListener{
        void onLearingsClicked();
        void onShareAppClicked();
    }
}
