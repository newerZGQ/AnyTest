package com.zgq.wokao.data.realm.Paper;

import com.zgq.wokao.model.paper.IExamPaper;
import com.zgq.wokao.model.paper.info.IPaperInfo;

import java.util.List;

/**
 * Created by zgq on 2017/2/27.
 */

public interface IPaperInfoDao extends IScheduleDao {
    public List<IPaperInfo> getAllPaperInfo();

    public List<IPaperInfo> getPaperInfosInSchdl();

    public void star(IExamPaper paper);

    public void unStar(IExamPaper paper);

    public void setTitle(IExamPaper paper, String title);

    public void addToSchedule(IExamPaper paper);

    public void removeFromSchedule(IExamPaper paper);

    public void setLastStudyDate(IExamPaper paper);
}
