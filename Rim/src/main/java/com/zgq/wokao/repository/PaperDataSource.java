package com.zgq.wokao.repository;

import com.zgq.wokao.entity.paper.NormalExamPaper;

/**
 * Created by zgq on 2017/11/20.
 */

public interface PaperDataSource {
    void saveExamPaper(NormalExamPaper examPaper);
    void deleteExamPaper(String paperId);
}
