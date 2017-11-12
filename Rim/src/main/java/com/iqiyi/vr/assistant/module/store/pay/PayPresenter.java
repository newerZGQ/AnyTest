package com.iqiyi.vr.assistant.module.store.pay;

import com.iqiyi.vr.assistant.api.RetrofitService;
import com.iqiyi.vr.assistant.api.bean.BaseResponse;
import com.iqiyi.vr.assistant.module.base.IBasePresenter;
import com.orhanobut.logger.Logger;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by wangyancong on 2017/9/6.
 */

public class PayPresenter implements IBasePresenter {

    private final AppPayActivity view;
    private long appId;
    private String authCookie = "e7aym3UY5dsm1ivpAJkThm1m2HqVPzzI1H4aJfuAMHlv1qowJ8EOXOMqm11A9CJFGXwZjjW3f";

    public PayPresenter(AppPayActivity view, long appId) {
        this.view = view;
        this.appId = appId;
    }

    @Override
    public void getData(boolean isRefresh) {
        //PrefUtils.getString(view, CommonConstant.PREF_AUTHCOOKIE_KEY);
        RetrofitService.getOrderNum(appId, authCookie)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        view.showLoading();
                    }
                })
                .compose(view.<BaseResponse<String>>bindToLife())
                .subscribe(new Subscriber<BaseResponse<String>>() {
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
                    public void onNext(BaseResponse<String> data) {
                        Logger.w("onNext  msg :" + data.getMsg() + " order :" + data.getData());
                        view.loadData(data.getData());
                    }
                });
    }
}
