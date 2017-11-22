package com.zgq.wokao.dao;

import com.zgq.wokao.entity.paper.NormalExamPaper;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmResults;

public class RimDaoSource implements RimDao {

    Realm realm = Realm.getDefaultInstance();

    @Inject
    public RimDaoSource(){}

    @Override
    public void saveExamPaper(NormalExamPaper paper) {
        realm.beginTransaction();
        realm.copyFromRealm(paper);
        realm.commitTransaction();
    }

    @Override
    public void deleteExamPaper(NormalExamPaper paper) {
        realm.beginTransaction();
        paper.cascadeDelete();
        realm.commitTransaction();
    }

    @Override
    public @Nullable NormalExamPaper queryExamPaper(String paperId) {
        return realm.where(NormalExamPaper.class)
                .equalTo("paperInfo.id", paperId)
                .findFirst();
    }
}
