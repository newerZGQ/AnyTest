package com.zgq.wokao.module.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zgq.wokao.R;
import com.zgq.wokao.RimApplication;
import com.zgq.wokao.injector.components.ApplicationComponent;
import com.zgq.wokao.module.IPresenter;
import com.zgq.wokao.module.IView;

import javax.inject.Inject;

public abstract class BasePreferenceFragment<T extends IPresenter> extends PreferenceFragmentCompat implements IView<T> {

    @Inject
    protected T presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        daggerInject();
        this.addPreferencesFromResource(attachLayoutRes());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
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
