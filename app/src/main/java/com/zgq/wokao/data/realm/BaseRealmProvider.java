package com.zgq.wokao.data.realm;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by zgq on 2017/2/11.
 */

public abstract class BaseRealmProvider<T extends RealmObject> implements DataProvider<T> {

    private Realm realm = Realm.getDefaultInstance();
    private Class entityClass;
    public void setClass(Class entityClass){
        this.entityClass = entityClass;
    }

    @Override
    public Realm getRealm() {
        return realm;
    }

    @Override
    public void save(T entity) {
        realm.beginTransaction();
        realm.copyToRealm(entity);
        realm.commitTransaction();
    }

    @Override
    public void delete(T entity) {
        entity.deleteFromRealm();
    }

    @Override
    public void update(T entity) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(entity);
        realm.commitTransaction();
    }

    @Override
    public T query(String id) {
        if (id == null || id.equals("")){
            return null;
        }
        if (entityClass == null){
            return null;
        }
        RealmQuery<T> query = realm.where(entityClass);
        query.equalTo("id",id);
        RealmResults<T> results = query.findAll();
        if (results.isEmpty()){
            return null;
        }
        return results.get(0);
    }
}
