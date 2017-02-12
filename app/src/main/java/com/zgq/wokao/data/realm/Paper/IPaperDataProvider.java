package com.zgq.wokao.data.realm.Paper;

import com.zgq.wokao.model.paper.ExamPaperInfo;
import com.zgq.wokao.model.paper.NormalExamPaper;
import com.zgq.wokao.model.search.Searchable;

import java.util.List;

/**
 * Created by zgq on 2017/2/11.
 */

public interface IPaperDataProvider {
    public List<NormalExamPaper> getAllPaper();
    public List<ExamPaperInfo> getAllPaperInfo();
    public List<Searchable> search(String query);
}
