package com.zgq.wokao.data.realm.search;

import com.zgq.wokao.Util.DateUtil;
import com.zgq.wokao.data.realm.BaseRealmProvider;
import com.zgq.wokao.model.search.SearchHistory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by zgq on 2017/2/15.
 */

public class SearchHistoryProvider extends BaseRealmProvider<SearchHistory> implements ISearchHistoryProvider {
    private Realm realm = Realm.getDefaultInstance();

    private SearchHistoryProvider() {
        setClass(SearchHistory.class);
    }

    private static class InstanceHolder {
        private static SearchHistoryProvider instance = new SearchHistoryProvider();
    }

    public static SearchHistoryProvider getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public void save(SearchHistory entity) {
        if (entity == null || entity.getContent().equals("")) {
            return;
        }
        RealmResults<SearchHistory> list = realm.where(SearchHistory.class).equalTo("content", entity.getContent()).findAll();
        if (list.size() == 0) {
            super.save(entity);
        } else {
            queryHistory(entity.getContent());
        }

        return;
    }

    @Override
    public void update(SearchHistory entity) {
        entity.setDate(DateUtil.getCurrentDate());
        entity.setCount(entity.getCount() + 1);
        super.update(entity);
    }

    @Override
    public SearchHistory query(String content) {
        RealmResults<SearchHistory> results = realm.where(SearchHistory.class).equalTo("content", content).findAll();
        if (results.size() == 0) {
            return null;
        }
        return results.get(0);
    }

    /**
     * @param content
     */
    public void queryHistory(String content) {
        realm.beginTransaction();
        SearchHistory history = query(content);
        history.setDate(DateUtil.getCurrentDate());
        history.setCount(history.getCount() + 1);
        realm.commitTransaction();
    }

    @Override
    public List<SearchHistory> findRelative(String query, Integer limit) {
        RealmResults<SearchHistory> realmResults = realm.where(SearchHistory.class).
                contains("content", query).
                findAll().
                sort("count", Sort.DESCENDING);
        ArrayList<SearchHistory> results = new ArrayList<>();
        if (limit == null) {
            limit = 20;
        }
        if (limit == 0) {
            return results;
        }
        for (int i = 0; i < realmResults.size(); i++) {
            if (i == limit) {
                break;
            }
            results.add(realmResults.get(i));
        }
        return results;
    }

    @Override
    public List<SearchHistory> findLastest(Integer limit) {
        RealmResults<SearchHistory> realmResults = realm.where(SearchHistory.class).
                findAll().
                sort("date", Sort.DESCENDING);
        ArrayList<SearchHistory> results = new ArrayList<>();
        if (limit == null) {
            limit = 20;
        }
        if (limit == 0) {
            return results;
        }
        for (int i = 0; i < realmResults.size(); i++) {
            if (i == limit) {
                break;
            }
            results.add(realmResults.get(i));
        }
        return results;
    }

}
