package com.iqiyi.vr.assistant.module.store.main;

import com.iqiyi.vr.assistant.api.RetrofitService;
import com.iqiyi.vr.assistant.api.bean.BaseResponse;
import com.iqiyi.vr.assistant.api.bean.app.AppResponse;
import com.iqiyi.vr.assistant.module.base.IPayPresenter;
import com.iqiyi.vr.assistant.util.CommonConstant;
import com.iqiyi.vr.assistant.util.PrefUtils;
import com.orhanobut.logger.Logger;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by wangyancong on 2017/9/1.
 */

public class StorePresenter implements IPayPresenter {

    private final StoreFragment view;

    public StorePresenter(StoreFragment view) {
        this.view = view;
    }

    @Override
    public void getData(final boolean isRefresh) {
        RetrofitService.getAppList()
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (!isRefresh) {
                            view.showLoading();
                        }
                    }
                })
                .compose(view.<AppResponse>bindToLife())
                .subscribe(new Subscriber<AppResponse>() {
                    @Override
                    public void onCompleted() {
                        Logger.w("onCompleted" + isRefresh);
                        if (isRefresh) {
                            view.finishRefresh();
                        } else {
                            view.hideLoading();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.toString() + " " + isRefresh);
                        if (isRefresh) {
                            view.finishRefresh();
                            // 可以提示对应的信息，但不更新界面
                        } else {
                            view.showNetError();
                        }
                    }

                    @Override
                    public void onNext(AppResponse data) {
                        view.loadData(data.getData().getList());
                    }

                });
    }

    @Override
    public void pushData(long appId, int orderId) {
        RetrofitService.syncApp("", "", "", orderId,
                PrefUtils.getString(view.getContext(), CommonConstant.PREF_USERID_KEY),
                appId,
                "123, 456")//deviceIds
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
