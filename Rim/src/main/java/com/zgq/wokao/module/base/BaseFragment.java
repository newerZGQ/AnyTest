package com.zgq.wokao.module.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.components.support.RxFragment;
import com.zgq.wokao.RimApplication;
import com.zgq.wokao.injector.components.ApplicationComponent;
import com.zgq.wokao.module.BasePresenter;
import com.zgq.wokao.module.BaseView;

import javax.inject.Inject;

import butterknife.ButterKnife;

public abstract class BaseFragment<T extends BasePresenter> extends RxFragment implements BaseView<T> {

    private View mRootView;

    @Inject
    protected T presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(attachLayoutRes(), null);
        daggerInject();
        presenter.takeView(this);
        ButterKnife.bind(this, mRootView);
        initViews();
        return mRootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dropView();
    }

    protected LifecycleTransformer bindToLife() {
        return this.<T>bindToLifecycle();
    }

    protected abstract void daggerInject();

    protected abstract int attachLayoutRes();

    protected abstract void initViews();

    protected ApplicationComponent getAppComponent() {
        return RimApplication.getAppComponent();
    }

}
