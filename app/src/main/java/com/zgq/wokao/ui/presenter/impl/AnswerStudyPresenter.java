package com.zgq.wokao.ui.presenter.impl;

import com.zgq.wokao.action.paper.impl.PaperAction;
import com.zgq.wokao.model.paper.question.IQuestion;
import com.zgq.wokao.ui.presenter.IAnswerStudyPresenter;
import com.zgq.wokao.ui.view.IStudyAnswerView;

/**
 * Created by zgq on 2017/4/10.
 */

public class AnswerStudyPresenter implements IAnswerStudyPresenter {
    private PaperAction paperAction = PaperAction.getInstance();
    private IStudyAnswerView studyAnswerView;
    public AnswerStudyPresenter(IStudyAnswerView studyAnswerView){
        this.studyAnswerView = studyAnswerView;
    }

    @Override
    public void updateQuestion(String questionId, IQuestion question) {
        paperAction.updateQuestion(questionId,question);
    }

    @Override
    public void startEditMode() {

    }
}
