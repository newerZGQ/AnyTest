package com.zgq.wokao.ui.presenter.impl;

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
    private IPapersView papersView;
    private IPaperAction paperAction = PaperAction.getInstance();

    private ArrayList<IPaperInfo> paperInfos;

    public PapersPresenter(IPapersView papersView) {
        this.papersView = papersView;

    }

    public ArrayList<IPaperInfo> getPaperInfos(boolean needUpdate) {
        if (needUpdate || paperInfos == null){
            paperInfos = (ArrayList<IPaperInfo>) paperAction.getAllPaperInfo();
        }
        return paperInfos;
    }

    @Override
    public void setPaperList() {
        papersView.setPaperList(getPaperInfos(false));
    }
}
