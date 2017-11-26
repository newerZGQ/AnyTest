package com.zgq.wokao.repository;

import java.util.List;

import io.reactivex.Flowable;
import io.realm.RealmModel;

public interface RimDataSource extends PaperDataSource {
    <T extends RealmModel> Flowable<T> copyFromRealm(T t);

    <T extends RealmModel> Flowable<List<T>> copyFromRealm(Iterable<T> realmObjects);

    <T extends RealmModel> Flowable<T> copyToRealmOrUpdate(T t);
}
