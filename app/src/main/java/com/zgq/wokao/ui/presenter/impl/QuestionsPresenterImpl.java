package com.zgq.wokao.ui.presenter.impl;

import android.util.Log;

import com.zgq.wokao.action.paper.impl.PaperAction;
import com.zgq.wokao.action.viewdata.ViewDataAction;
import com.zgq.wokao.model.paper.IExamPaper;
import com.zgq.wokao.model.viewdate.QstData;
import com.zgq.wokao.ui.presenter.IQuestionsPresenter;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/8/20.
 */

public class QuestionsPresenterImpl implements IQuestionsPresenter {

    private String paperId;

    private ArrayList<QstData> qstLists;

    public QuestionsPresenterImpl(){

    }

    public QuestionsPresenterImpl(String paperId){
        this.paperId = paperId;
        Log.d("paperid",paperId);
        qstLists = ViewDataAction.getInstance().getQstData(PaperAction.getInstance().queryById(paperId));
    }

    @Override
    public ArrayList<QstData> getQstLists() {
        return qstLists;
    }
}
