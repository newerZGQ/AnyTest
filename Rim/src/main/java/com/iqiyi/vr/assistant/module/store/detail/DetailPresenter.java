package com.iqiyi.vr.assistant.module.store.detail;

import com.iqiyi.vr.assistant.api.RetrofitService;
import com.iqiyi.vr.assistant.api.bean.BaseResponse;
import com.iqiyi.vr.assistant.api.bean.app.DetailResponse;
import com.iqiyi.vr.assistant.module.base.IPayPresenter;
import com.iqiyi.vr.assistant.util.CommonConstant;
import com.iqiyi.vr.assistant.util.PrefUtils;
import com.orhanobut.logger.Logger;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by wangyancong on 2017/9/2.
 */

public class DetailPresenter implements IPayPresenter {

    private final AppDetailActivity view;
    private long appId;
    private String appPkgName;
    private int appVer;

    public DetailPresenter(AppDetailActivity view, long appId, String appPkgName, int appVer) {
        this.view = view;
        this.appId = appId;
        this.appPkgName = appPkgName;
        this.appVer = appVer;
    }

    @Override
    public void getData(boolean isRefresh) {
        RetrofitService.getAppDetail(appId, appPkgName, appVer, 1244044173)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        view.showLoading();
                    }
                })
                .compose(view.<DetailResponse>bindToLife())
                .subscribe(new Subscriber<DetailResponse>() {
                    @Override
                    public void onCompleted() {
                        Logger.w("onCompleted");
                        view.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.toString());
                        view.showNetError();
                    }

                    @Override
                    public void onNext(DetailResponse data) {
                        Logger.w("onNext  detail data :" + data);
                        view.loadData(data);
                    }
                });
    }

    @Override
    public void pushData(long appId, int orderId) {
        RetrofitService.syncApp("", "", "", orderId,
                PrefUtils.getString(view, CommonConstant.PREF_USERID_KEY),
                appId,
                "123,456")
                .subscribe(new Subscriber<BaseResponse<String>>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(BaseResponse<String> data) {
                        Logger.d("sync app code :" + data.getCode());
                    }
                });
    }
}
