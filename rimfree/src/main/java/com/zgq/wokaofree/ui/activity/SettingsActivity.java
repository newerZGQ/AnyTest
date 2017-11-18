package com.zgq.wokaofree.ui.activity;

import android.os.Bundle;

import com.zgq.wokaofree.R;
import com.zgq.wokaofree.Util.FBShare;
import com.zgq.wokaofree.Util.WXShare;
import com.zgq.wokaofree.ui.fragment.impl.LearningFragment;
import com.zgq.wokaofree.ui.fragment.impl.SettingsFragment;

/**
 * Created by zhangguoqiang on 2017/10/21.
 */

public class SettingsActivity extends BaseActivity implements SettingsFragment.SettingsFragmentListener {
    SettingsFragment settingsFragment = new SettingsFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, settingsFragment).commit();
    }

    @Override
    public void onLearingsClicked() {
        openActivity(LearningActivity.class);
    }

    @Override
    public void onShareAppClicked() {
//        WXShare.regToWx(this);
//        WXShare.shareApp(this);
        FBShare.FacebookShare(this);
    }
}
