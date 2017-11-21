package com.zgq.wokao.dao;

import com.zgq.wokao.entity.paper.NormalExamPaper;

/**
 * Created by zgq on 2017/11/22.
 */

public interface RimDao {
    void saveExamPaper(NormalExamPaper paper);
    void deleteExamPaper(NormalExamPaper paper);
    NormalExamPaper queryExamPaper(String paperId);
}
