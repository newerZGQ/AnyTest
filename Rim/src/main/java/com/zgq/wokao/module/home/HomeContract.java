package com.zgq.wokao.module.home;

import com.zgq.wokao.entity.paper.info.ExamPaperInfo;
import com.zgq.wokao.entity.paper.info.Schedule;
import com.zgq.wokao.entity.summary.StudySummary;
import com.zgq.wokao.module.IPresenter;
import com.zgq.wokao.module.IView;

import io.realm.RealmList;
import io.realm.RealmResults;

public interface HomeContract {
    interface MainView extends IView<MainPresenter> {
        void showTotalRecord(StudySummary studySummary);
    }

    interface ScheduleView extends IView<SchedulePresenter> {
        void setDetail(Schedule schedule);
        void setSchedulePapers(RealmResults<ExamPaperInfo> examPaperInfos);
    }

    interface PaperView extends IView<PaperPresenter> {
    }

    interface MainPresenter extends IPresenter<MainView> {
        void loadSummary();
    }


    interface SchedulePresenter extends IPresenter<ScheduleView> {
        void loadSchedules();
        void updateDetail(int index);
    }


    interface PaperPresenter extends IPresenter<PaperView> {
    }
}
