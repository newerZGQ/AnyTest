package com.zgq.wokao.ui.fragment.impl;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.zgq.wokao.R;

/**
 * Created by zhangguoqiang on 2017/10/21.
 */

public class SettingsFragment extends PreferenceFragment {

    private SettingsFragmentListener mListener;

    public SettingsFragment() {
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.addPreferencesFromResource(R.xml.pref_settings);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
