package com.zgq.wokaofree.ui.presenter;

import com.zgq.wokaofree.model.paper.info.IPaperInfo;
import com.zgq.wokaofree.model.paper.question.info.IQuestionInfo;
import com.zgq.wokaofree.model.viewdate.QstData;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/8/20.
 */

public interface IQuestionsPresenter {
    ArrayList<QstData> getQstLists();
    IPaperInfo getPaperInfo();
}
