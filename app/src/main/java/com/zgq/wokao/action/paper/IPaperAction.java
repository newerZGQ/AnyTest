package com.zgq.wokao.action.paper;

import com.zgq.wokao.model.paper.IExamPaper;

/**
 * Created by zgq on 2017/2/28.
 */

public interface IPaperAction {
    public void setPaperStar(IExamPaper paper,boolean stared);
    public void openSchedule(IExamPaper paper);
    public void closeSchedule(IExamPaper paper);
    public void setDailyCount(IExamPaper paper,int count);

}
