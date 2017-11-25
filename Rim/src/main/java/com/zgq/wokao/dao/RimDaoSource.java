package com.zgq.wokao.dao;

import com.zgq.wokao.entity.paper.NormalExamPaper;
import com.zgq.wokao.entity.paper.info.ExamPaperInfo;
import com.zgq.wokao.entity.summary.StudySummary;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmResults;

public class RimDaoSource implements RimDao {

    Realm realm = Realm.getDefaultInstance();

    @Inject
    public RimDaoSource(){}

    @Override
    public void saveExamPaper(@Nonnull NormalExamPaper paper) {
        realm.beginTransaction();
        realm.copyToRealm(paper);
        realm.commitTransaction();
    }

    @Override
    public void deleteExamPaper(@Nonnull NormalExamPaper paper) {
        realm.beginTransaction();
        paper.cascadeDelete();
        realm.copyFromRealm(paper);
        realm.commitTransaction();
    }

    @Override
    public @Nullable NormalExamPaper queryExamPaper(@Nonnull String paperId) {
        return realm.where(NormalExamPaper.class)
                .equalTo("paperInfo.id", paperId)
                .findFirst();
    }

    @Nullable
    @Override
    public RealmResults<NormalExamPaper> getAllExamPaper() {
        return realm.where(NormalExamPaper.class)
                .findAll();
    }

    @Nonnull
    @Override
    public RealmResults<ExamPaperInfo> getAllExamPaperInfo() {
        return realm.where(ExamPaperInfo.class)
                .findAll();
    }

    @Override
    public void setSked(@Nonnull NormalExamPaper paper, boolean skedState) {
        realm.beginTransaction();
        paper.getPaperInfo().getSchedule().setInSked(skedState);
        realm.commitTransaction();
    }

    @Override
    public void saveSummary(StudySummary studySummary) {
        realm.beginTransaction();
        realm.copyToRealm(studySummary);
        realm.commitTransaction();
    }

    @Override
    public StudySummary getStudySummary() {
        return realm.where(StudySummary.class)
                .findFirst();
    }

    @Override
    public <T extends RealmModel> T copyFromRealm(T t) {
        return realm.copyFromRealm(t);
    }

    @Override
    public <T extends RealmModel> List<T> copyFromRealm(Iterable<T> realmObjects) {
        return realm.copyFromRealm(realmObjects);
    }
}
