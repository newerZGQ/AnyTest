package com.zgq.wokao.module.base;

import android.support.annotation.NonNull;

import com.zgq.wokao.module.IPresenter;
import com.zgq.wokao.module.IView;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter<T extends IView> implements IPresenter<T> {

    protected T view;

    @NonNull
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void takeView(IView view) {
        this.view = (T) view;
    }

    @Override
    public void dropView() {
        view = null;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
