package com.zgq.wokao.module.welcome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zgq.wokao.R;
import com.zgq.wokao.module.base.BaseActivity;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected int attachLayoutRes() {
        return 0;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }
}
