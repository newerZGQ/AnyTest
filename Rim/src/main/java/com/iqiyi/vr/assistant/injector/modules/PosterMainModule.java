package com.iqiyi.vr.assistant.injector.modules;

import com.iqiyi.vr.assistant.adapter.PhotoPagerAdapter;
import com.iqiyi.vr.assistant.api.bean.app.CaptureModel;
import com.iqiyi.vr.assistant.injector.PerActivity;
import com.iqiyi.vr.assistant.module.base.IBasePresenter;
import com.iqiyi.vr.assistant.module.store.poster.BigPosterActivity;
import com.iqiyi.vr.assistant.module.store.poster.BigPosterPresenter;

import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wangyancong on 2017/9/6.
 */
@Module
public class PosterMainModule {

    private final BigPosterActivity view;
    private final List<CaptureModel> data;

    public PosterMainModule(BigPosterActivity view, List<CaptureModel> data) {
        this.view = view;
        this.data = data;
    }

    @PerActivity
    @Provides
    public IBasePresenter providePresenter() {
        return new BigPosterPresenter(view, data);
    }

    @PerActivity
    @Provides
    public PhotoPagerAdapter provideAdapter() {
        return new PhotoPagerAdapter(view);
    }
}
