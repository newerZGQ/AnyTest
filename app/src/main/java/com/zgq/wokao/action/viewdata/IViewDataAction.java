package com.zgq.wokao.action.viewdata;

import com.zgq.wokao.model.paper.IExamPaper;
import com.zgq.wokao.model.viewdate.QstData;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/3/19.
 */

public interface IViewDataAction {
    public ArrayList<QstData> getQstData(IExamPaper paper);
}
