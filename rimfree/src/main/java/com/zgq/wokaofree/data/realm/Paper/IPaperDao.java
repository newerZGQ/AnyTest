package com.zgq.wokaofree.data.realm.Paper;

import com.zgq.wokaofree.model.paper.IExamPaper;
import com.zgq.wokaofree.model.paper.info.ExamPaperInfo;
import com.zgq.wokaofree.model.paper.NormalExamPaper;
import com.zgq.wokaofree.model.search.Searchable;

import java.util.List;

/**
 * Created by zgq on 2017/2/11.
 */

public interface IPaperDao extends IPaperInfoDao, IPaperSchdlDao {
    List<NormalExamPaper> getAllPaper();

    List<NormalExamPaper> getAllPaperInSchdl();

    List<Searchable> search(String query);

    List<ExamPaperInfo> getSchedulePapers();

    void deleteById(String id);

    void updateStudyInfo(IExamPaper paper, boolean isCorrect);
}
