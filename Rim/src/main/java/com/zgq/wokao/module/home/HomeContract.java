package com.zgq.wokao.module.home;

import com.zgq.wokao.entity.paper.info.ExamPaperInfo;
import com.zgq.wokao.entity.summary.StudySummary;
import com.zgq.wokao.module.IPresenter;
import com.zgq.wokao.module.IView;

import io.realm.RealmList;

public interface HomeContract {
    interface MainView extends IView<MainPresenter> {
        void showDayCurve(StudySummary studySummary);

        void showTotalRecord(StudySummary studySummary);
    }

    interface ScheduleView extends IView<SchedulePresenter> {
        void setDetail();
        void setSchedulePaper(RealmList<ExamPaperInfo> examPaperInfos);
    }

    interface PaperView extends IView<PaperPresenter> {
    }

    interface MainPresenter extends IPresenter<MainView> {
        void loadSummary();
    }


    interface SchedulePresenter extends IPresenter<ScheduleView> {
        void loadSchedule();
        void updateDetail(int index);
    }


    interface PaperPresenter extends IPresenter<PaperView> {
    }
}
