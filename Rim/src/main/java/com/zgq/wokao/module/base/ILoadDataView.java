package com.zgq.wokao.module.base;

import com.zgq.wokao.module.BaseView;

/**
 * Created by wangyancong on 2017/8/23.
 * 加载数据的界面接口
 */

public interface ILoadDataView<T> extends BaseView {
    /**
     * 加载数据
     *
     * @param data 数据
     */
    void loadData(T data);
}
