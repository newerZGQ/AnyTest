package com.zgq.wokao.repository;

import com.zgq.wokao.dao.RimDaoSource;
import com.zgq.wokao.entity.paper.NormalExamPaper;

import com.google.common.base.Optional;
import com.zgq.wokao.entity.paper.info.ExamPaperInfo;
import com.zgq.wokao.entity.summary.StudySummary;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import io.reactivex.Flowable;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RimRepository implements RimDataSource {

    @Inject
    RimDaoSource daoSource;

    private PaperRepository paperRepository;

    @Inject
    public RimRepository(PaperRepository paperRepository){
        this.paperRepository = paperRepository;
    }

    @Override
    public void saveExamPaper(NormalExamPaper examPaper) {
        paperRepository.saveExamPaper(examPaper);
    }

    @Override
    public void deleteExamPaper(NormalExamPaper examPaper) {
        paperRepository.deleteExamPaper(examPaper);
    }

    @Override
    public Flowable<Optional<NormalExamPaper>> queryPaper(String paperId) {
        return paperRepository.queryPaper(paperId);
    }

    @Nonnull
    @Override
    public Flowable<RealmResults<ExamPaperInfo>> getAllExamPaperInfo() {
        return paperRepository.getAllExamPaperInfo();
    }

    @Override
    public void setSked(NormalExamPaper paper, boolean skedState) {
        paperRepository.setSked(paper, skedState);
    }

    @Override
    public void saveSummary(@Nonnull final StudySummary studySummary) {
        getStudySummary().subscribe(studySummaryOptional -> {
            if (!studySummaryOptional.isPresent()){
                paperRepository.saveSummary(studySummary);
            }
        });
    }

    @Override
    public Flowable<Optional<StudySummary>> getStudySummary() {
        return paperRepository.getStudySummary();
    }

    @Override
    public <T extends RealmModel> Flowable<T> copyFromRealm(T t) {
        return Flowable.just(daoSource.copyFromRealm(t));
    }

    @Override
    public <T extends RealmModel> Flowable<List<T>> copyFromRealm(Iterable<T> realmObjects) {
        return Flowable.just(daoSource.copyFromRealm(realmObjects));
    }
}
