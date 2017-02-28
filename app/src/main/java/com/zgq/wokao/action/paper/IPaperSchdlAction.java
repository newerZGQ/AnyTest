package com.zgq.wokao.action.paper;

import com.zgq.wokao.model.paper.IExamPaper;

/**
 * Created by zgq on 2017/2/28.
 */

public interface IPaperSchdlAction extends IPaperAction {
    public void openSchedule(IExamPaper paper);
    public void closeSchedule(IExamPaper paper);
    public void setDailyCount(IExamPaper paper,int count);
    //每次答题后调用来更新学习记录
    public void updateDailyRecord(IExamPaper paper);
}
