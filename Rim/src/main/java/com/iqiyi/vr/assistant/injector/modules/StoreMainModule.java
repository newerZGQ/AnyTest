package com.iqiyi.vr.assistant.injector.modules;

import com.iqiyi.vr.assistant.adapter.AppListAdapter;
import com.iqiyi.vr.assistant.injector.PerFragment;
import com.iqiyi.vr.assistant.module.base.IBasePresenter;
import com.iqiyi.vr.assistant.module.base.IPayPresenter;
import com.iqiyi.vr.assistant.module.store.main.StoreFragment;
import com.iqiyi.vr.assistant.module.store.main.StorePresenter;
import com.iqiyi.vr.recyclerviewhelper.adapter.BaseQuickAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wangyancong on 2017/8/31.
 */
@Module
public class StoreMainModule {

    private final StoreFragment view;

    public StoreMainModule(StoreFragment view) {
        this.view = view;
    }

    @PerFragment
    @Provides
    public IPayPresenter providePresenter() {
        return new StorePresenter(view);
    }

    @PerFragment
    @Provides
    public BaseQuickAdapter provideAdapter() {
        return new AppListAdapter(view.getContext());
    }
}
