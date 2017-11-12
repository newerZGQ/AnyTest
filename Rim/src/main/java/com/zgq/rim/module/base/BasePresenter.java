package com.zgq.rim.module.base;

/**
 * Created by wangyancong on 2017/8/23.
 * 基础 Presenter
 */

public interface BasePresenter<T extends BaseView> {
    /**
     * Binds presenter with a view when resumed. The Presenter will perform initialization here.
     *
     * @param view the view associated with this presenter
     */
    void takeView(T view);

    /**
     * Drops the reference to the view when destroyed
     */
    void dropView();
}
