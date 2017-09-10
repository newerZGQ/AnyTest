package com.zgq.wokao.ui.presenter;

import com.zgq.wokao.model.paper.info.IPaperInfo;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/3/5.
 */

public interface IPapersPresenter {
    void deletePaper(String paperId);

    void addToSchedule(String paperId);

    void removeFromSchedule(String paperId);

    int getPaperCount();

    ArrayList<IPaperInfo> getPaperInfos();
}
