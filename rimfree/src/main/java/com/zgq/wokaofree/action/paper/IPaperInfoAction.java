package com.zgq.wokaofree.action.paper;

import com.zgq.wokaofree.model.paper.IExamPaper;
import com.zgq.wokaofree.model.paper.info.IPaperInfo;

import java.util.List;

/**
 * Created by zgq on 2017/2/28.
 */

public interface IPaperInfoAction {
    List<IPaperInfo> getAllPaperInfo();

    List<IPaperInfo> getPaperInfosInSchdl();

    void deletePaperInfo(IPaperInfo paperInfo);

    void star(IExamPaper paper);

    void star(String paperId);

    void unStar(IExamPaper paper);

    void unstar(String paperId);

    void setTitle(IExamPaper paper, String title);

    void addToSchedule(IExamPaper paper);

    void removeFromSchedule(IExamPaper paper);

    void addToSchedule(String paperId);

    void removeFromSchedule(String paperId);

    void setLastStudyDate(IExamPaper paper);
}
