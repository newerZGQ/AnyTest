package com.zgq.wokao.ui.presenter.impl;

import android.util.Log;

import com.zgq.wokao.action.paper.IPaperAction;
import com.zgq.wokao.action.paper.impl.PaperAction;
import com.zgq.wokao.model.paper.IExamPaper;
import com.zgq.wokao.model.paper.NormalExamPaper;
import com.zgq.wokao.model.paper.info.IPaperInfo;
import com.zgq.wokao.ui.presenter.IPapersPresenter;
import com.zgq.wokao.ui.view.IPapersView;

import java.util.ArrayList;
import java.util.List;

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
        getPaperInfos(false);
    }

    public ArrayList<IPaperInfo> getPaperInfos(boolean needUpdate) {
        if (needUpdate || paperInfos == null || paperInfos.size() == 0){
            paperInfos = (ArrayList<IPaperInfo>) paperAction.getAllPaperInfo();
        }
        List<NormalExamPaper> papers = paperAction.getAllPaper();
        return paperInfos;
    }

    @Override
    public void notifyDataChanged() {
        getPaperInfos(true);
        papersView.notifyDataChanged(paperInfos);
    }

    @Override
    public void initPapersList() {
        papersView.initPaperList(paperInfos);
    }

    @Override
    public void deletePaper(String paperId) {
        paperAction.deleteExamPaper(paperId);
        papersView.notifyDataChanged(paperInfos);
    }

    @Override
    public void addToSchedule(String paperId) {
        paperAction.addToSchedule(paperId);
        getPaperInfos(true);
    }

    @Override
    public void removeFromSchedule(String paperId) {
        paperAction.removeFromSchedule(paperId);
        getPaperInfos(true);
    }
}
