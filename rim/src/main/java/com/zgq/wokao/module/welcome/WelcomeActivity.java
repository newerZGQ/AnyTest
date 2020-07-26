package com.zgq.wokao.module.welcome;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.zgq.wokao.R;
import com.zgq.wokao.injector.components.DaggerWelcomeComponent;
import com.zgq.wokao.injector.modules.WelcomeModule;
import com.zgq.wokao.module.base.BaseActivity;
import com.zgq.wokao.module.home.HomeActivity;
import com.zgq.wokao.module.maintab.MainTabActivity;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

public class WelcomeActivity extends BaseActivity<WelcomeContract.MainPresenter>
        implements WelcomeContract.MainView{

    private static final int MY_PERMISSIONS_REQUEST_OPERATE_SDCARD = 972;

    @Inject
    SplashFragment fragment;
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

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            goHomeActivity();
            return;
        }
        final String[] permissions = {"Manifest.permission.WRITE_EXTERNAL_STORAGE",
                "Manifest.permission.READ_EXTERNAL_STORAGE"};
        int i = ContextCompat.checkSelfPermission(this, permissions[0]);
        if (i != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(WelcomeActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_OPERATE_SDCARD);
            return;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_OPERATE_SDCARD:
                if (grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    presenter.checkSample(getApplicationContext());
                } else {
                    showToast(getResources().getString(R.string.storage_waring));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void goHomeActivity() {
        openActivity(MainTabActivity.class);
        finish();
    }
}
