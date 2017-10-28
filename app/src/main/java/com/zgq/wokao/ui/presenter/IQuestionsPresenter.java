package com.zgq.wokao.ui.presenter;

import com.zgq.wokao.model.paper.info.IPaperInfo;
import com.zgq.wokao.model.paper.question.info.IQuestionInfo;
import com.zgq.wokao.model.viewdate.QstData;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/8/20.
 */

public interface IQuestionsPresenter {
    ArrayList<QstData> getQstLists();
    IPaperInfo getPaperInfo();
}
