package com.zgq.wokaofree.ui.presenter.impl;

import com.zgq.wokaofree.action.paper.IPaperAction;
import com.zgq.wokaofree.action.paper.impl.PaperAction;
import com.zgq.wokaofree.model.paper.info.IPaperInfo;
import com.zgq.wokaofree.ui.presenter.IPapersPresenter;
import com.zgq.wokaofree.ui.view.IPapersView;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/3/5.
 */

public class PapersPresenter implements IPapersPresenter {
    private static final String TAG = "PapersPresenter";
    private IPapersView papersView;
    private IPaperAction paperAction = PaperAction.getInstance();

    private ArrayList<IPaperInfo> paperInfos;

    public PapersPresenter(IPapersView papersView) {
        this.papersView = papersView;
        updateDatas();
    }

    private ArrayList<IPaperInfo> updateDatas() {
        paperInfos = (ArrayList<IPaperInfo>) paperAction.getAllPaperInfo();
        return paperInfos;
    }

    @Override
    public void deletePaper(String paperId) {
        paperAction.deleteExamPaper(paperId);
        updateDatas();
    }

    @Override
    public void addToSchedule(String paperId) {
        paperAction.addToSchedule(paperId);
        updateDatas();
    }

    @Override
    public void removeFromSchedule(String paperId) {
        paperAction.removeFromSchedule(paperId);
        updateDatas();
    }

    @Override
    public int getPaperCount() {
        return paperInfos.size();
    }

    @Override
    public ArrayList<IPaperInfo> getPaperInfos() {
        return paperInfos;
    }
}
