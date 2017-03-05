package com.zgq.wokao.data.realm.Paper;

import com.zgq.wokao.model.paper.IExamPaper;
import com.zgq.wokao.model.paper.info.ExamPaperInfo;
import com.zgq.wokao.model.paper.NormalIExamPaper;
import com.zgq.wokao.model.search.Searchable;

import java.util.List;

/**
 * Created by zgq on 2017/2/11.
 */

public interface IPaperDao extends IPaperInfoDao,IPaperSchdlDao{
    public List<NormalIExamPaper> getAllPaper();
    public List<NormalIExamPaper> getAllPaperInSchdl();
    public List<Searchable> search(String query);
    public List<ExamPaperInfo> getSchedulePapers();
}
