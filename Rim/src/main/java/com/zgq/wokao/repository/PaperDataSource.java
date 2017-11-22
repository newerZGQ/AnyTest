package com.zgq.wokao.repository;

import com.google.common.base.Optional;
import com.zgq.wokao.entity.paper.NormalExamPaper;

import io.reactivex.Flowable;

/**
 * Created by zgq on 2017/11/20.
 */

public interface PaperDataSource {
    void saveExamPaper(NormalExamPaper examPaper);
    void deleteExamPaper(NormalExamPaper examPaper);
    Flowable<Optional<NormalExamPaper>> queryPaper(String paperId);
}
