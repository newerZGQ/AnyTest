package com.zgq.wokao.module.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.components.support.RxFragment;
import com.zgq.wokao.R;
import com.zgq.wokao.widget.EmptyLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseFragment<T extends BasePresenter> extends RxFragment
        implements BaseView, EmptyLayout.OnRetryListener {
    /**
     * 注意，资源的ID一定要一样
     */
    @Nullable
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;
    @Inject
    protected T presenter;

    protected Context mContext;
    //缓存Fragment view
    private View mRootView;
    private boolean mIsMulti = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(attachLayoutRes(), null);
            ButterKnife.bind(this, mRootView);
            initInjector();
            initViews();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint() && mRootView != null && !mIsMulti) {
            mIsMulti = true;
            updateViews(false);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && isVisible() && mRootView != null && !mIsMulti) {
            mIsMulti = true;
            updateViews(false);
        } else {
            super.setUserVisibleHint(isVisibleToUser);
        }
    }

    public void showLoading() {
        if (emptyLayout != null) {
            emptyLayout.setEmptyStatus(EmptyLayout.STATUS_LOADING);
        }
    }

    public void hideLoading() {
        if (emptyLayout != null) {
            emptyLayout.hide();
        }
    }

    public void showNetError() {
        if (emptyLayout != null) {
            emptyLayout.setEmptyStatus(EmptyLayout.STATUS_NO_NET);
            emptyLayout.setRetryListener(this);
        }
    }

    @Override
    public void onRetry() {
        updateViews(false);
    }

    @Override
    public LifecycleTransformer bindToLife() {
        return this.<T>bindToLifecycle();
    }

    @Override
    public void setToolbar(int res) {

    }

    @Override
    public void showToast(String message) {

    }

    /**
     * 获取 AppComponent
     *
     * @return AppComponent
     */
//    protected AppComponent getAppComponent() {
//        return RimApplication.getAppComponent();
//    }

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    protected abstract int attachLayoutRes();

    /**
     * Dagger 注入
     */
    protected abstract void initInjector();

    /**
     * 初始化视图控件
     */
    protected abstract void initViews();

    /**
     * 更新视图控件
     *
     * @param isRefresh 新增参数，用来判断是否为下拉刷新调用，下拉刷新的时候不应该再显示加载界面和异常界面
     */
    protected abstract void updateViews(boolean isRefresh);
}
