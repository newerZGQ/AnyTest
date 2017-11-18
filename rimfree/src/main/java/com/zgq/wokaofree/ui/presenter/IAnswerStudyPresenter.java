package com.zgq.wokaofree.ui.presenter;

import com.zgq.wokaofree.model.paper.question.IQuestion;

/**
 * Created by zgq on 2017/4/10.
 */

public interface IAnswerStudyPresenter {
    void updateQuestion(String questionId, IQuestion question);

    void startEditMode();
}
