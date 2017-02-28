package com.zgq.wokao.action.paper;

import com.zgq.wokao.model.paper.IExamPaper;

/**
 * Created by zgq on 2017/2/28.
 */

public interface IPaperInfoAction extends IPaperAction {
    public void star(IExamPaper paper);
    public void unStar(IExamPaper paper);
    public void setTitle(IExamPaper paper,String title);
    public void addToSchedule(IExamPaper paper);
    public void removeFromSchedule(IExamPaper paper);
}
