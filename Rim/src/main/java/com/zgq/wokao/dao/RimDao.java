package com.zgq.wokao.dao;

import com.zgq.wokao.entity.paper.NormalExamPaper;
import com.zgq.wokao.entity.paper.info.ExamPaperInfo;
import com.zgq.wokao.entity.search.SearchHistory;
import com.zgq.wokao.entity.summary.StudySummary;
import com.zgq.wokao.module.search.entity.HistorySuggestion;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmResults;

public interface RimDao {
    void saveExamPaper(@Nonnull NormalExamPaper paper);
    void deleteExamPaper(@Nonnull NormalExamPaper paper);
    @Nullable
    NormalExamPaper queryExamPaper(@Nonnull String paperId);
    @Nullable
    RealmResults<NormalExamPaper> getAllExamPaper();
    @Nonnull
    RealmResults<ExamPaperInfo> getAllExamPaperInfo();
    void setSked(@Nonnull NormalExamPaper paper, boolean skedState);

    void saveSummary(StudySummary studySummary);
    StudySummary getStudySummary();

    void saveSearchHistory(@Nonnull SearchHistory SearchHistory);
    @Nonnull
    RealmResults<SearchHistory> getSearchHistory();

    <T extends RealmModel> T copyFromRealm(T  t);
    <T extends RealmModel> List<T> copyFromRealm(Iterable<T> realmObjects);
    <T extends RealmModel> T copyToRealmOrUpdate(T t);
}
