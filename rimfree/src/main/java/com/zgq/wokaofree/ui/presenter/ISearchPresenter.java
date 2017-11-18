package com.zgq.wokaofree.ui.presenter;

import com.zgq.wokaofree.model.paper.QuestionType;

/**
 * Created by zgq on 2017/9/18.
 */

public interface ISearchPresenter {
    QuestionType getLastStudyType(String paperId);
    int getLastStudyIndex(String paperId);
}
