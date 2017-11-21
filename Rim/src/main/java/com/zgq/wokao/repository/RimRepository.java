package com.zgq.wokao.repository;

import android.content.Context;

import com.zgq.wokao.entity.paper.NormalExamPaper;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by zgq on 2017/11/20.
 */

public class RimRepository implements RimDataSource {

    private PaperRepository paperRepository;

    @Inject
    public RimRepository(PaperRepository paperRepository){
        this.paperRepository = paperRepository;
    }

    @Override
    public void saveExamPaper(NormalExamPaper examPaper) {

    }

    @Override
    public void deleteExamPaper(String paperId) {

    }
}
