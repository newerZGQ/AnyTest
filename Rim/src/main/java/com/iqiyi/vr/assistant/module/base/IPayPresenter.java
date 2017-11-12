package com.iqiyi.vr.assistant.module.base;

/**
 * Created by wangyancong on 2017/9/7.
 */

public interface IPayPresenter extends IBasePresenter {

    /**
     * 上传app信息
     * @param appId
     * @param orderId
     */
    void pushData(long appId, int orderId);
}
