package com.zgq.wokao.repository;

import com.zgq.wokao.dao.RimDaoSource;
import com.zgq.wokao.entity.paper.NormalExamPaper;

import javax.inject.Inject;

/**
 * Created by zgq on 2017/11/20.
 */

public class PaperRepository implements PaperDataSource {

    @Inject
    RimDaoSource daoSource;

    @Inject
    public PaperRepository(){}

    @Override
    public void saveExamPaper(NormalExamPaper examPaper) {
        daoSource.saveExamPaper(examPaper);
    }

    @Override
    public void deleteExamPaper(NormalExamPaper examPaper) {
        daoSource.deleteExamPaper(examPaper);
    }

    @Override
    public NormalExamPaper queryPaper(String paperId) {
        return daoSource.queryExamPaper(paperId);
    }


}
