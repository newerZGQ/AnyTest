package com.zgq.wokao.module.base;

import com.zgq.wokao.module.BasePresenter;

import rx.functions.Action1;

/**
 * Created by wangyancong on 2017/8/23.
 * RxBus Presenter
 */

public interface IRxBusPresenter extends BasePresenter {
    /**
     * 注册
     *
     * @param eventType
     * @param <T>
     */
    <T> void registerRxBus(Class<T> eventType, Action1<T> action);

    /**
     * 注销
     */
    void unregisterRxBus();
}
