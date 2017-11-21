package com.zgq.wokao.module.home;

import com.zgq.wokao.entity.summary.StudySummary;
import com.zgq.wokao.entity.summary.TotalDailyCount;
import com.zgq.wokao.module.BasePresenter;
import com.zgq.wokao.module.BaseView;

import io.realm.RealmList;

/**
 * Created by zhangguoqiang on 2017/11/12.
 */

public interface HomeContract {
    interface View extends BaseView<Presenter>{
        void showDayCurve(StudySummary studySummary);
        void showTotalRecord(StudySummary studySummary);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
