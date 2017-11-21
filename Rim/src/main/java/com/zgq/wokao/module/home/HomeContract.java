package com.zgq.wokao.module.home;

import com.zgq.wokao.entity.summary.StudySummary;
import com.zgq.wokao.module.BasePresenter;
import com.zgq.wokao.module.BaseView;

public interface HomeContract {
    interface MainView extends BaseView<MainPresenter> {
        void showDayCurve(StudySummary studySummary);

        void showTotalRecord(StudySummary studySummary);
    }

    interface ScheduleView extends BaseView<SchedulePresenter>{
    }

    interface PaperView extends BaseView<PaperPresenter> {
    }

    interface MainPresenter extends BasePresenter<MainView> {
    }


    interface SchedulePresenter extends BasePresenter<ScheduleView> {
    }


    interface PaperPresenter extends BasePresenter<PaperView> {
    }
}
