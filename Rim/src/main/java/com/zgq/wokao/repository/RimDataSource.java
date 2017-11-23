package com.zgq.wokao.repository;

import java.util.List;

import io.reactivex.Flowable;
import io.realm.RealmModel;
import io.realm.RealmObject;

/**
 * Created by zgq on 2017/11/20.
 */

public interface RimDataSource extends PaperDataSource {
    <T extends RealmModel> Flowable<T> copyFromRealm(T  t);
    <T extends RealmModel> Flowable<List<T>> copyFromRealm(Iterable<T> realmObjects);
}
