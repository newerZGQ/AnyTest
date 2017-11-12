package com.iqiyi.vr.assistant.module.home;


import com.iqiyi.vr.assistant.module.base.IBasePresenter;

/**
 * Created by wangyancong on 2017/8/28.
 */

public class HomePresenter implements IBasePresenter {

    private HomeActivity activity;


    public HomePresenter(HomeActivity activity) {
        this.activity = activity;
    }

    @Override
    public void getData(boolean isRefresh) {
        activity.loadData(null);
    }

}
