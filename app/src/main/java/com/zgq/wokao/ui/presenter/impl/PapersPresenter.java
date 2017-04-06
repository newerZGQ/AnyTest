package com.zgq.wokao.ui.presenter.impl;

import android.util.Log;

import com.zgq.wokao.action.paper.IPaperAction;
import com.zgq.wokao.action.paper.impl.PaperAction;
import com.zgq.wokao.model.paper.info.IPaperInfo;
import com.zgq.wokao.ui.presenter.IPapersPresenter;
import com.zgq.wokao.ui.view.IPapersView;

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
        getPaperInfos(false);
    }

    public ArrayList<IPaperInfo> getPaperInfos(boolean needUpdate) {
        if (needUpdate || paperInfos == null || paperInfos.size() == 0){
            paperInfos = (ArrayList<IPaperInfo>) paperAction.getAllPaperInfo();
        }
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
}
