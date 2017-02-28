package com.zgq.wokao.data.realm.Paper;

import com.zgq.wokao.model.paper.IExamPaper;
import com.zgq.wokao.model.paper.info.ExamIPaperInfo;

import java.util.List;

/**
 * Created by zgq on 2017/2/27.
 */

public interface IPaperInfoDao {
    public List<ExamIPaperInfo> getAllPaperInfo();
    public void star(IExamPaper paper);
    public void unStar(IExamPaper paper);
    public void setTitle(IExamPaper paper,String title);
    public void addToSchedule(IExamPaper paper);
    public void removeFromSchedule(IExamPaper paper);
}
