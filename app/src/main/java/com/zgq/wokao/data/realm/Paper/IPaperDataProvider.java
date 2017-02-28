package com.zgq.wokao.data.realm.Paper;

import com.zgq.wokao.model.paper.info.ExamIPaperInfo;
import com.zgq.wokao.model.paper.NormalIExamPaper;
import com.zgq.wokao.model.search.Searchable;

import java.util.List;

/**
 * Created by zgq on 2017/2/11.
 */

public interface IPaperDataProvider {
    public List<NormalIExamPaper> getAllPaper();
    public List<ExamIPaperInfo> getAllPaperInfo();
    public List<Searchable> search(String query);
    public List<ExamIPaperInfo> getSchedulePapers();
}
