package com.iqiyi.vr.assistant.module.home;

import android.content.Intent;

import com.iqiyi.vr.assistant.R;
import com.iqiyi.vr.assistant.module.base.BaseActivity;
import com.iqiyi.vr.assistant.util.RxHelper;
import com.iqiyi.vr.assistant.widget.SimpleButton;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by wangyancong on 2017/8/23.
 */

public class SplashActivity extends BaseActivity {

    @BindView(R.id.sb_skip)
    SimpleButton sbSkip;

    private boolean isSkip = false;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initInjector() {
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        RxHelper.countdown(5)
                .compose(this.<Integer>bindToLife())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        _doSkip();
                    }

                    @Override
                    public void onError(Throwable e) {
                        _doSkip();
                    }

                    @Override
                    public void onNext(Integer integer) {
                        sbSkip.setText(getString(R.string.splash_drop) + integer);
                    }
                });
    }

    private void _doSkip() {
        if (!isSkip) {
            isSkip = true;
            finish();
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            overridePendingTransition(R.anim.hold, R.anim.zoom_in_exit);
        }
    }

    @Override
    public void onBackPressed() {
        // 不响应后退键
        return;
    }

    @OnClick(R.id.sb_skip)
    public void onClick() {
        _doSkip();
    }
}
