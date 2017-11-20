package com.zgq.wokao.module.welcome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.RimContextCompat;
import com.zgq.wokao.injector.components.DaggerWelcomeComponent;
import com.zgq.wokao.injector.components.WelcomeComponent;
import com.zgq.wokao.injector.modules.WelcomeModule;
import com.zgq.wokao.module.base.BaseFragment;


import javax.inject.Inject;

import butterknife.BindView;

public class WelcomeFragment extends BaseFragment<WelcomeContract.Presenter> implements WelcomeContract.View{

    private static final String TAG = WelcomeFragment.class.getSimpleName();

    @BindView(R.id.tips)
    TextView tip;

    private WelcomeComponent component;

    @Inject
    RimContextCompat contextCompat;

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
        component = DaggerWelcomeComponent.builder()
                .welcomeModule(new WelcomeModule())
                .applicationComponent(getAppComponent())
                .build();
        component.inject(this);
        presenter.takeView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.selectTip(contextCompat.getStringArray(R.array.welcome_tips));
    }

    @Override
    public void setTip(String message) {
        tip.setText(message);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dropView();
    }
}
