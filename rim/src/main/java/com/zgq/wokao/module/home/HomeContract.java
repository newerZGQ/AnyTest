package com.zgq.wokao.module.home;

import com.zgq.wokao.entity.paper.NormalExamPaper;
import com.zgq.wokao.entity.paper.info.ExamPaperInfo;
import com.zgq.wokao.entity.paper.info.Schedule;
import com.zgq.wokao.entity.paper.question.QuestionType;
import com.zgq.wokao.module.home.summary.StudySummary;
import com.zgq.wokao.module.IPresenter;
import com.zgq.wokao.module.IView;

import java.util.List;

public interface HomeContract {
    interface MainView extends IView<MainPresenter> {
        void showTotalRecord(StudySummary studySummary);
    }

    interface ScheduleView extends IView<SchedulePresenter> {
        void setDetail(Schedule schedule);
        void setSchedulePapers(List<ExamPaperInfo> examPaperInfos);
        void notifyDataChanged();
        void showEmptyView();
        void hideEmptyView();
        void startQuestionsActivity(String paperId);
        void startStudy(String paperId, QuestionType type, String questionId);
    }

    interface PaperView extends IView<PaperPresenter> {
        void setPaperListData(List<NormalExamPaper> examPapers);
        void notifyDataChanged();
        void showEmptyView();
        void hideEmptyView();
    }

    interface MainPresenter extends IPresenter<MainView> {
        void loadSummary();
        boolean checkPaperCount();
    }


    interface SchedulePresenter extends IPresenter<ScheduleView> {
        void loadSchedules(boolean forceUpdate);
        void updateDetail(int index);
        void updateTask(int index, int task);
        void loadQuestions(int position);
        void loadStudyInfo(String paperInfoId);
    }


    interface PaperPresenter extends IPresenter<PaperView> {
        void loadAllPapers(boolean forceUpdate);
        void deletePaper(NormalExamPaper paper);
        void removeFromSchedule(NormalExamPaper paper);
        void addToSchedule(NormalExamPaper paper);
    }
}
