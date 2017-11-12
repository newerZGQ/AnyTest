package com.iqiyi.vr.assistant.module.store.poster;

import com.iqiyi.vr.assistant.api.bean.app.CaptureModel;
import com.iqiyi.vr.assistant.module.base.IBasePresenter;

import java.util.List;

/**
 * Created by wangyancong on 2017/9/4.
 */

public class BigPosterPresenter implements IBasePresenter {

    private final BigPosterActivity view;
    private final List<CaptureModel> data;

    public BigPosterPresenter(BigPosterActivity view, List<CaptureModel> data) {
        this.view = view;
        this.data = data;
    }

    @Override
    public void getData(boolean isRefresh) {
        view.loadData(data);
    }
}
