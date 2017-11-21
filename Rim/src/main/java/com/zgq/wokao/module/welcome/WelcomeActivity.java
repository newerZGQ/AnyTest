package com.zgq.wokao.module.welcome;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zgq.wokao.R;
import com.zgq.wokao.injector.components.DaggerWelcomeComponent;
import com.zgq.wokao.injector.modules.WelcomeModule;
import com.zgq.wokao.module.base.BaseActivity;
import com.zgq.wokao.module.home.HomeActivity;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

public class WelcomeActivity extends BaseActivity<WelcomeContract.MainPresenter> implements WelcomeContract.MainView{
    @Inject
    WelcomeFragment fragment;
    @Inject
    Context context;

    @Override
    protected void daggerInject() {
        DaggerWelcomeComponent.builder()
                .welcomeModule(new WelcomeModule())
                .applicationComponent(getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initViews() {
        addFragment(R.id.contentFrame,fragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TimerTask task = new TimerTask() {
            public void run() {
                checkPermissions();
            }
        };
        Timer timer = new Timer(true);
        timer.schedule(task, 1000);
    }

    private void checkPermissions(){
        openActivity(HomeActivity.class);
    }
}
