package com.zgq.wokaofree.ui.presenter.impl;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.zgq.wokaofree.action.paper.impl.PaperAction;
import com.zgq.wokaofree.action.viewdata.ViewDataAction;
import com.zgq.wokaofree.model.paper.IExamPaper;
import com.zgq.wokaofree.model.paper.info.IPaperInfo;
import com.zgq.wokaofree.model.paper.question.info.IQuestionInfo;
import com.zgq.wokaofree.model.viewdate.QstData;
import com.zgq.wokaofree.ui.presenter.IQuestionsPresenter;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/8/20.
 */

public class QuestionsPresenterImpl implements IQuestionsPresenter {
    private static String TAG = QuestionsPresenterImpl.class.getSimpleName();

    private String paperId;

    private ArrayList<QstData> qstLists;
    private IPaperInfo paperInfo;

    private QuestionsPresenterImpl() {

    }

    public QuestionsPresenterImpl(String paperId) {
        this.paperId = paperId;
    }

    @Override
    public ArrayList<QstData> getQstLists() {
        qstLists = ViewDataAction.getInstance().getQstData(PaperAction.getInstance().queryById(paperId));
        return qstLists;
    }

    @Override
    public IPaperInfo getPaperInfo() {
        paperInfo = PaperAction.getInstance().queryById(paperId).getPaperInfo();
        return paperInfo;
    }
}
