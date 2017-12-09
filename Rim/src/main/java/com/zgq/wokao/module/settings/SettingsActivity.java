package com.zgq.wokao.module.settings;

import android.view.View;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.injector.components.DaggerSettingsComponent;
import com.zgq.wokao.injector.modules.SettingsModule;
import com.zgq.wokao.module.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;

public class SettingsActivity extends BaseActivity<SettingsContract.MainPresenter>
        implements SettingsContract.MainView{

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_back)
    TextView toolbarBack;

    @Inject
    SettingsFragment settingsFragment;

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
        return R.layout.activity_settings;
    }

    @Override
    protected void initViews() {
        addFragment(R.id.fragment_container,settingsFragment);
        toolbarTitle.setText(getString(R.string.settings));
        toolbarBack.setOnClickListener(view -> finish());
    }
}
