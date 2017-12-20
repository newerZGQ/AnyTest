package com.zgq.wokao.repository;

import com.google.common.base.Optional;
import com.zgq.wokao.dao.RimDaoSource;
import com.zgq.wokao.entity.paper.NormalExamPaper;
import com.zgq.wokao.entity.paper.info.ExamPaperInfo;
import com.zgq.wokao.entity.paper.question.IQuestion;
import com.zgq.wokao.entity.summary.StudySummary;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import io.reactivex.Flowable;
import io.realm.RealmResults;

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
    public Flowable<Optional<NormalExamPaper>> queryPaper(String paperId) {
        return Flowable.just(Optional.fromNullable(daoSource.queryExamPaper(paperId)));
    }

    @Override
    public Flowable<RealmResults<NormalExamPaper>> getAllExamPaper() {
        return Flowable.just(daoSource.getAllExamPaper());
    }

    @Nonnull
    @Override
    public Flowable<RealmResults<ExamPaperInfo>> getAllExamPaperInfo() {
        return Flowable.just(daoSource.getAllExamPaperInfo());
    }

    @Override
    public void setSked(NormalExamPaper paper, boolean skedState) {
        daoSource.setSked(paper,skedState);
    }

    @Override
    public void saveSummary(@Nonnull StudySummary studySummary) {
        daoSource.saveSummary(studySummary);
    }

    @Override
    public Flowable<Optional<StudySummary>> getStudySummary() {
        return Flowable.just(Optional.fromNullable(daoSource.getStudySummary()));
    }

    @Override
    public void starQuestion(IQuestion question, boolean star) {
        daoSource.starQuestion(question,star);
    }
}
