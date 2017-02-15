package com.zgq.wokao.data.realm.search;

import com.zgq.wokao.data.realm.BaseRealmProvider;
import com.zgq.wokao.model.search.SearchHistory;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by zgq on 2017/2/15.
 */

public class SearchHistoryProvider extends BaseRealmProvider<SearchHistory> implements ISearchHistoryProvider {
    private Realm realm = Realm.getDefaultInstance();
    private SearchHistoryProvider(){
        setClass(SearchHistory.class);
    }
    private static class InstanceHolder{
        private static SearchHistoryProvider instance = new SearchHistoryProvider();
    }
    public static SearchHistoryProvider getInstance(){
        return InstanceHolder.instance;
    }

    @Override
    public void save(SearchHistory entity) {
        if (entity == null || entity.getContent().equals("")) {
            return;
        }
        realm.beginTransaction();
        RealmResults<SearchHistory> list = realm.where(SearchHistory.class).equalTo("content", entity.getContent()).findAll();
        if (list.size() == 0){
            super.save(entity);
        }else{
            SearchHistory tmp = list.first();
            tmp.setCount(tmp.getCount()+1);
        }
        realm.commitTransaction();
        return;
    }


    @Override
    public List<SearchHistory> findRelative(String query, Integer limit) {
        RealmResults<SearchHistory> realmResults = realm.where(SearchHistory.class).
                contains("content",query).
                findAll().
                sort("count", Sort.DESCENDING);
        ArrayList<SearchHistory> results = new ArrayList<>();
        if (limit == null){
            limit = 20;
        }
        if (limit == 0 ) {
            return results;
        }
        for (int i = 0; i<realmResults.size() ; i++) {
            if (i == limit) {
                break;
            }
            results.add(realmResults.get(i));
        }
        return results;
    }
}
