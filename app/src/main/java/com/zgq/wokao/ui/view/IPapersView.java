package com.zgq.wokao.ui.view;

import com.zgq.wokao.model.paper.info.IPaperInfo;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/3/5.
 */

public interface IPapersView {
    public void initPaperList(ArrayList<IPaperInfo> paperInfos);
    public void notifyDataChanged(ArrayList<IPaperInfo> paperInfos);
}
