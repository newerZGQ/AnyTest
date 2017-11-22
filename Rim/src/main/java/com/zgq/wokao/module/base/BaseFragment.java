package com.zgq.wokao.module.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zgq.wokao.RimApplication;
import com.zgq.wokao.injector.components.ApplicationComponent;
import com.zgq.wokao.module.BasePresenter;
import com.zgq.wokao.module.BaseView;

import javax.inject.Inject;

import butterknife.ButterKnife;

public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView<T> {

    private View mRootView;

    @Inject
    protected T presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(attachLayoutRes(), null);
        daggerInject();
        ButterKnife.bind(this, mRootView);
        initViews();
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.takeView(this);
        presenter.subscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
        presenter.dropView();
    }

    protected abstract void daggerInject();

    protected abstract int attachLayoutRes();

    protected abstract void initViews();

    protected ApplicationComponent getAppComponent() {
        return RimApplication.getAppComponent();
    }

}
