package com.zgq.wokao.ui.presenter.impl;

import com.zgq.wokao.action.paper.impl.PaperAction;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.ui.presenter.ISearchPresenter;

/**
 * Created by zgq on 2017/9/18.
 */

public class SearchPresenter implements ISearchPresenter {

    PaperAction paperAction = PaperAction.getInstance();

    @Override
    public QuestionType getLastStudyType(String paperId) {
        QuestionType type = QuestionType.valueOf(paperAction.queryById(paperId).getPaperInfo().getSchedule().getLastStudyType());
        return type;
    }

    @Override
    public int getLastStudyIndex(String paperId) {
        return paperAction.queryById(paperId).getPaperInfo().getSchedule().getLastStudyNum();
    }
}
