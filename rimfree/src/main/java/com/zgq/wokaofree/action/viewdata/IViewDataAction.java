package com.zgq.wokaofree.action.viewdata;

import com.zgq.wokaofree.model.paper.IExamPaper;
import com.zgq.wokaofree.model.viewdate.QstData;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/3/19.
 */

public interface IViewDataAction {
    ArrayList<QstData> getQstData(IExamPaper paper);
}
