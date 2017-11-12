package com.iqiyi.vr.assistant.injector.modules;

import com.iqiyi.vr.assistant.adapter.ViewPagerAdapter;
import com.iqiyi.vr.assistant.injector.PerActivity;
import com.iqiyi.vr.assistant.module.base.IBasePresenter;
import com.iqiyi.vr.assistant.module.home.HomeActivity;
import com.iqiyi.vr.assistant.module.home.HomePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wangyancong on 2017/8/25.
 */
@Module
public class HomeMainModule {

    private final HomeActivity view;

    public HomeMainModule(HomeActivity view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    public IBasePresenter providePresenter() {
        return new HomePresenter(view);
    }

    @PerActivity
    @Provides
    public ViewPagerAdapter providePagerAdapter() {
        return new ViewPagerAdapter(view.getSupportFragmentManager());
    }
}
