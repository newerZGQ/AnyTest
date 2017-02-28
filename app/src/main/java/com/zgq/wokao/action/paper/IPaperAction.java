package com.zgq.wokao.action.paper;

import com.zgq.wokao.model.paper.IExamPaper;

/**
 * Created by zgq on 2017/2/28.
 */

public interface IPaperAction extends IPaperInfoAction,IPaperSchdlAction{
    public void addExamPaper();
    public void deleteExamPaper();
}
