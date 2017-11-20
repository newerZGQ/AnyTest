package com.zgq.wokao.module.base;

import com.zgq.wokao.module.BaseView;

public interface ILoadDataView<T> extends BaseView {
    /**
     * 加载数据
     *
     * @param data 数据
     */
    void loadData(T data);
}
