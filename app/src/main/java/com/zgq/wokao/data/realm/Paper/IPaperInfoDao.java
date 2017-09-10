package com.zgq.wokao.data.realm.Paper;

import com.zgq.wokao.model.paper.IExamPaper;
import com.zgq.wokao.model.paper.info.IPaperInfo;

import java.util.List;

/**
 * Created by zgq on 2017/2/27.
 */

public interface IPaperInfoDao extends IScheduleDao {
    List<IPaperInfo> getAllPaperInfo();

    List<IPaperInfo> getPaperInfosInSchdl();

    void star(IExamPaper paper);

    void unStar(IExamPaper paper);

    void setTitle(IExamPaper paper, String title);

    void addToSchedule(IExamPaper paper);

    void removeFromSchedule(IExamPaper paper);

    void setLastStudyDate(IExamPaper paper);
}
