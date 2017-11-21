package com.zgq.wokao.module.home;

import com.zgq.wokao.entity.paper.info.ExamPaperInfo;
import com.zgq.wokao.entity.summary.StudySummary;
import com.zgq.wokao.module.BasePresenter;
import com.zgq.wokao.module.BaseView;

import io.realm.RealmList;

public interface HomeContract {
    interface MainView extends BaseView<MainPresenter> {
        void showDayCurve(StudySummary studySummary);

        void showTotalRecord(StudySummary studySummary);
    }

    interface ScheduleView extends BaseView<SchedulePresenter>{
        void setDetail();
        void setSchedulePaper(RealmList<ExamPaperInfo> examPaperInfos);
    }

    interface PaperView extends BaseView<PaperPresenter> {
    }

    interface MainPresenter extends BasePresenter<MainView> {
    }


    interface SchedulePresenter extends BasePresenter<ScheduleView> {
        void loadSchedule();
        void updateDetail(int index);
    }


    interface PaperPresenter extends BasePresenter<PaperView> {
    }
}
