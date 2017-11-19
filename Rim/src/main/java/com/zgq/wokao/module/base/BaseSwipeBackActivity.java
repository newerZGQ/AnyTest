package com.zgq.wokao.module.base;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zgq.wokao.module.BasePresenter;
import com.zgq.wokao.widget.SwipeBackLayout;

import lombok.Getter;

/**
 * 滑动退出Activity，参考：https://github.com/ikew0ng/SwipeBackLayout
 */

public abstract class BaseSwipeBackActivity<T extends BasePresenter> extends BaseActivity<T> {

    @Getter
    private SwipeBackLayout swipeBackLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        swipeBackLayout = new SwipeBackLayout(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        swipeBackLayout.attachToActivity(this, SwipeBackLayout.EDGE_LEFT);
        // 触摸边缘变为屏幕宽度的1/2
        swipeBackLayout.setEdgeSize(getResources().getDisplayMetrics().widthPixels / 2);
    }
}
