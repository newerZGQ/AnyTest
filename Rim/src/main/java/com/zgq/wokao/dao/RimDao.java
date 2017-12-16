package com.zgq.wokao.dao;

import com.zgq.wokao.entity.paper.NormalExamPaper;
import com.zgq.wokao.entity.paper.info.ExamPaperInfo;
import com.zgq.wokao.entity.search.SearchHistory;
import com.zgq.wokao.entity.summary.StudySummary;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.realm.RealmModel;
import io.realm.RealmResults;

public interface RimDao {
    void saveExamPaper(@Nonnull NormalExamPaper paper);
    void deleteExamPaper(@Nonnull NormalExamPaper paper);
    @Nullable
    NormalExamPaper queryExamPaper(@Nonnull String paperId);
    @Nonnull
    RealmResults<NormalExamPaper> getAllExamPaper();
    @Nonnull
    RealmResults<ExamPaperInfo> getAllExamPaperInfo();
    void setSked(@Nonnull NormalExamPaper paper, boolean skedState);

    void saveSummary(StudySummary studySummary);
    @Nullable
    StudySummary getStudySummary();

    void saveSearchHistory(@Nonnull SearchHistory searchHistory);
    void updateSearchHistory(SearchHistory entity);
    @Nullable
    SearchHistory querySearchHistory(String content);
    @Nonnull
    List<SearchHistory> getLastestSearchHistory(int limit);
    @Nonnull
    List<SearchHistory> findRelativeSearchHistory(String query, Integer limit);

    <T extends RealmModel> T copyFromRealm(T  t);
    <T extends RealmModel> List<T> copyFromRealm(Iterable<T> realmObjects);
    <T extends RealmModel> T copyToRealmOrUpdate(T t);
}
