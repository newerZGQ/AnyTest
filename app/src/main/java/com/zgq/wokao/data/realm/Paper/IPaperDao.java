package com.zgq.wokao.data.realm.Paper;

import com.zgq.wokao.model.paper.IExamPaper;
import com.zgq.wokao.model.paper.info.ExamPaperInfo;
import com.zgq.wokao.model.paper.NormalExamPaper;
import com.zgq.wokao.model.search.Searchable;

import java.util.List;

/**
 * Created by zgq on 2017/2/11.
 */

public interface IPaperDao extends IPaperInfoDao, IPaperSchdlDao {
    public List<NormalExamPaper> getAllPaper();

    public List<NormalExamPaper> getAllPaperInSchdl();

    public List<Searchable> search(String query);

    public List<ExamPaperInfo> getSchedulePapers();

    public void deleteById(String id);

    public void updateStudyInfo(IExamPaper paper, boolean isCorrect);
}
