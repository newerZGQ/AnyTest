package com.zgq.wokao.data.realm.search;

import com.zgq.wokao.model.search.SearchHistory;

import java.util.List;

/**
 * Created by zgq on 2017/2/15.
 */

public interface ISearchHistoryProvider {
    public List<SearchHistory> findRelative(String query, Integer limit);
}
