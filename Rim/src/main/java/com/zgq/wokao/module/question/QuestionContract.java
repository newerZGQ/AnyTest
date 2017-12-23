package com.zgq.wokao.module.question;

import com.zgq.wokao.adapter.QuestionsInfoAdapter;
import com.zgq.wokao.entity.paper.info.ExamPaperInfo;
import com.zgq.wokao.entity.paper.question.QuestionType;
import com.zgq.wokao.module.IPresenter;
import com.zgq.wokao.module.IView;

import java.util.List;

public interface QuestionContract {
    interface View extends IView<Presenter> {
        void showQuestions(List<QuestionsInfoAdapter.QuestionsInfo> questions, ExamPaperInfo paperInfo);
        void startStudy(String paperId, QuestionType questionType);
    }

    interface Presenter extends IPresenter<View> {
        void loadQuestions(String paperId);
        void loadStudyQuestions(int position);
    }
}
