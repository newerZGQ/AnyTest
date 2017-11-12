package com.iqiyi.vr.assistant.injector.modules;

import com.iqiyi.vr.assistant.adapter.AppPosterAdapter;
import com.iqiyi.vr.assistant.injector.PerActivity;
import com.iqiyi.vr.assistant.module.base.IPayPresenter;
import com.iqiyi.vr.assistant.module.store.detail.AppDetailActivity;
import com.iqiyi.vr.assistant.module.store.detail.DetailPresenter;
import com.iqiyi.vr.recyclerviewhelper.adapter.BaseQuickAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wangyancong on 2017/9/2.
 */
@Module
public class DetailMainModule {

    private final AppDetailActivity view;
    private long appId;
    private String appPkgName;
    private int appVer;

    public DetailMainModule(AppDetailActivity view, long appId, String appPkgName, int appVer) {
        this.view = view;
        this.appId = appId;
        this.appPkgName = appPkgName;
        this.appVer = appVer;
    }

    @PerActivity
    @Provides
    public IPayPresenter providePresenter() {
        return new DetailPresenter(view, appId, appPkgName, appVer);
    }

    @PerActivity
    @Provides
    public BaseQuickAdapter provideAdapter() {
        return new AppPosterAdapter(view);
    }
}
