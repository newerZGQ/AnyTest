package com.zgq.rim.module.base;

import android.content.Intent;

import com.trello.rxlifecycle.LifecycleTransformer;

public interface BaseView<T extends BasePresenter> {

    LifecycleTransformer bindToLife();

    void setToolbar(int res);

    void showToast(String message);

    void startActivity(Intent intent);
}
