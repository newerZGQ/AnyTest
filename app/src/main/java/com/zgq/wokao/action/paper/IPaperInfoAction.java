package com.zgq.wokao.action.paper;

import com.zgq.wokao.model.paper.IExamPaper;
import com.zgq.wokao.model.paper.info.IPaperInfo;

import java.util.List;

/**
 * Created by zgq on 2017/2/28.
 */

public interface IPaperInfoAction {
    public List<IPaperInfo> getAllPaperInfo();
    public List<IPaperInfo> getPaperInfosInSchdl();
    public void deletePaperInfo(IPaperInfo paperInfo);
    public void star(IExamPaper paper);
    public void star(String paperId);
    public void unStar(IExamPaper paper);
    public void unstar(String paperId);
    public void setTitle(IExamPaper paper,String title);
    public void addToSchedule(IExamPaper paper);
    public void removeFromSchedule(IExamPaper paper);
    public void addToSchedule(String paperId);
    public void removeFromSchedule(String paperId);
    public void setLastStudyDate(IExamPaper paper);
}
