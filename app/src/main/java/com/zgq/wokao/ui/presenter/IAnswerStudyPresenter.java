package com.zgq.wokao.ui.presenter;

import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.paper.question.IQuestion;

/**
 * Created by zgq on 2017/4/10.
 */

public interface IAnswerStudyPresenter {
    public void updateQuestion(String questionId, IQuestion question);
    public void startEditMode();
}
