package com.zgq.wokao.repository;

import android.util.Log;

import com.google.common.base.Optional;
import com.zgq.wokao.dao.RimDaoSource;
import com.zgq.wokao.entity.paper.NormalExamPaper;
import com.zgq.wokao.entity.paper.info.ExamPaperInfo;
import com.zgq.wokao.entity.paper.question.IQuestion;
import com.zgq.wokao.entity.search.SearchHistory;
import com.zgq.wokao.entity.summary.StudySummary;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import io.reactivex.Flowable;
import io.realm.RealmModel;
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

    @Override
    public Flowable<Optional<NormalExamPaper>> queryPaperByInfoId(String paperInfoId) {
        return paperRepository.queryPaperByInfoId(paperInfoId);
    }

    @Override
    public Flowable<RealmResults<NormalExamPaper>> getAllExamPaper() {
        return paperRepository.getAllExamPaper();
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
    public void updateSummary(@Nonnull StudySummary studySummary) {
        paperRepository.saveSummary(studySummary);
    }

    @Override
    public Flowable<Optional<StudySummary>> getStudySummary() {
        return paperRepository.getStudySummary();
    }

    @Override
    public void starQuestion(IQuestion question, boolean star) {
        paperRepository.starQuestion(question,star);
    }

    @Override
    public <T extends RealmModel> Flowable<T> copyFromRealm(T t) {
        return Flowable.just(daoSource.copyFromRealm(t));
    }

    @Override
    public <T extends RealmModel> Flowable<List<T>> copyFromRealm(Iterable<T> realmObjects) {
        return Flowable.just(daoSource.copyFromRealm(realmObjects));
    }

    @Override
    public <T extends RealmModel> Flowable<T> copyToRealmOrUpdate(T t) {
        return Flowable.just(daoSource.copyToRealmOrUpdate(t));
    }

    @Override
    public void saveSearchHistory(@Nonnull SearchHistory searchHistory) {
        daoSource.saveSearchHistory(searchHistory);
    }

    @Override
    public void updateSearchHistory(SearchHistory entity) {
        daoSource.updateSearchHistory(entity);
    }

    @Nullable
    @Override
    public Flowable<Optional<SearchHistory>> querySearchHistory(String content) {
        return Flowable.just(Optional.fromNullable(daoSource.querySearchHistory(content)));
    }

    @Nonnull
    @Override
    public Flowable<SearchHistory> getLastestSearchHistory(int limit) {
        return Flowable.fromIterable(daoSource.getLastestSearchHistory(limit));
    }

    @Nonnull
    @Override
    public Flowable<SearchHistory> findRelativeSearchHistory(String query, Integer limit) {
        return Flowable.fromIterable(daoSource.findRelativeSearchHistory(query,limit));
    }
}
