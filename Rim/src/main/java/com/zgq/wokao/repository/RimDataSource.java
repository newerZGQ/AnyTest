package com.zgq.wokao.repository;

import com.google.common.base.Optional;
import com.zgq.wokao.entity.search.SearchHistory;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.reactivex.Flowable;
import io.realm.RealmModel;
import io.realm.RealmResults;

public interface RimDataSource extends PaperDataSource {
    <T extends RealmModel> Flowable<T> copyFromRealm(T t);

    <T extends RealmModel> Flowable<List<T>> copyFromRealm(Iterable<T> realmObjects);

    <T extends RealmModel> Flowable<T> copyToRealmOrUpdate(T t);

    void saveSearchHistory(@Nonnull SearchHistory searchHistory);
    void updateSearchHistory(SearchHistory entity);
    @Nullable
    Flowable<Optional<SearchHistory>> querySearchHistory(String content);
    @Nonnull
    Flowable<SearchHistory> getLastestSearchHistory(int limit);
    @Nonnull
    Flowable<RealmResults<SearchHistory>> findRelativeSearchHistory(String query, Integer limit);
}
