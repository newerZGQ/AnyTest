package com.zgq.wokao.module.welcome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.injector.components.DaggerWelcomeComponent;
import com.zgq.wokao.injector.modules.WelcomeModule;
import com.zgq.wokao.module.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;

public class WelcomeFragment extends BaseFragment implements WelcomeContract.View{

    private static final String TAG = WelcomeFragment.class.getSimpleName();

    @Inject
    WelcomeContract.Presenter presenter;

    @BindView(R.id.tips)
    TextView tip;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_welcome;
    }

    @Override
    protected void initViews() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DaggerWelcomeComponent.builder()
                .welcomeModule(new WelcomeModule())
                .applicationComponent(getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter == null){
            Log.d(TAG, "null presenter");
        }
        presenter.takeView(this);
        presenter.loadTip();
    }

    @Override
    public void setTip(String message) {
        tip.setText(message);
    }
}
