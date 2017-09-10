package com.zgq.wokao.data.realm;

import io.realm.Realm;

/**
 * Created by zgq on 2017/2/11.
 */

public interface DataProvider<T> {
    public Realm getRealm();

    public void save(T entity);

    public void delete(T entity);

    public void update(T entity);

    public T query(String id);
}
