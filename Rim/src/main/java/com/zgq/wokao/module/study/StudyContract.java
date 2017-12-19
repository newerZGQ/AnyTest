package com.zgq.wokao.module.study;

import com.zgq.wokao.entity.paper.question.IQuestion;
import com.zgq.wokao.entity.paper.question.QuestionType;
import com.zgq.wokao.module.IPresenter;
import com.zgq.wokao.module.IView;

import java.util.HashMap;
import java.util.List;


public interface StudyContract {
    interface MainView extends IView<MainPresenter>{}
    interface MainPresenter extends IPresenter<MainView>{}
    interface LearningView extends IView<LearningPresenter>{
        void showQuestions(List<IQuestion> questions);
        void showQuestionIndex(List<IQuestion> questions, HashMap<IQuestion, Boolean> answered);
        void notifyQuestionIndexChanged();
        void setStarState(boolean isStared);
        void setAnswerState(boolean answered);
    }
    interface LearningPresenter extends IPresenter<LearningView>{
        void initParams(String paperId, QuestionType type);
        void loadAllQuestions();
        void loadStarQuestions();
        void updateStudiedRecord(IQuestion question, boolean correct);
        void starQuestion(IQuestion question);
        void onLoadQuestion(IQuestion question);
    }
}
