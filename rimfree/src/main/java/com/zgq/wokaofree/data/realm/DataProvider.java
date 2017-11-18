package com.zgq.wokaofree.data.realm;

import io.realm.Realm;

/**
 * Created by zgq on 2017/2/11.
 */

public interface DataProvider<T> {
    Realm getRealm();

    void save(T entity);

    void delete(T entity);

    void update(T entity);

    T query(String id);
}
