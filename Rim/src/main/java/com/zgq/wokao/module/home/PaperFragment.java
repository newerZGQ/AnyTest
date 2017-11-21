package com.zgq.wokao.module.home;

import com.zgq.wokao.R;
import com.zgq.wokao.injector.components.DaggerHomeComponent;
import com.zgq.wokao.injector.modules.HomeModule;
import com.zgq.wokao.module.base.BaseFragment;

/**
 * Created by zhangguoqiang on 2017/11/21.
 */

public class PaperFragment extends BaseFragment<HomeContract.PaperPresenter> implements HomeContract.PaperView {
    @Override
    protected void daggerInject() {
        DaggerHomeComponent.builder()
                .applicationComponent(getAppComponent())
                .homeModule(new HomeModule())
                .build()
                .inject(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_paper;
    }

    @Override
    protected void initViews() {

    }
}
