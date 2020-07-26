package com.zgq.wokao.module.maintab;

import android.view.View;

import com.zgq.wokao.R;
import com.zgq.wokao.injector.components.DaggerMaintabComponent;
import com.zgq.wokao.injector.modules.MainTabModule;
import com.zgq.wokao.module.base.BaseActivity;
import com.zgq.wokao.module.maintab.homepage.HomepageFragment;
import com.zgq.wokao.module.maintab.manager.ManagerFragment;

import javax.inject.Inject;

public class MainTabActivity extends BaseActivity<MainTabContract.MainTabPresenter>
        implements MainTabContract.MainTabView, View.OnClickListener{

    private static final String TAG = MainTabActivity.class.getSimpleName();


    @Inject
    HomepageFragment homepageFragment;

    @Inject
    ManagerFragment managerFragment;


    @Override
    protected void daggerInject() {
        DaggerMaintabComponent.builder()
                .applicationComponent(getAppComponent())
                .mainTabModule(new MainTabModule())
                .build()
                .inject(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_maintab;
    }

    @Override
    protected void initViews() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content, homepageFragment)
                .commit();
    }

    @Override
    public void onClick(View view) {

    }
}

