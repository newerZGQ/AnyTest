package com.zgq.wokao.module.search;

import android.support.v4.app.Fragment;

import com.zgq.wokao.R;
import com.zgq.wokao.injector.components.DaggerSearchComponent;
import com.zgq.wokao.injector.modules.SearchModule;
import com.zgq.wokao.module.base.BaseActivity;

import javax.inject.Inject;

public class SearchActivity extends BaseActivity<SearchContract.MainPresenter> implements SearchContract.MainView{

    @Inject
    SearchFragment searchFragment;

    @Override
    protected void daggerInject() {
        DaggerSearchComponent.builder()
                .applicationComponent(getAppComponent())
                .searchModule(new SearchModule())
                .build()
                .inject(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_search;
    }

    @Override
    protected void initViews() {
        addFragment(R.id.fragment_container,searchFragment);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (!(((SearchFragment) fragment).onActivityBackPress())) {
            finish();
            super.onBackPressed();
        }
    }
}
