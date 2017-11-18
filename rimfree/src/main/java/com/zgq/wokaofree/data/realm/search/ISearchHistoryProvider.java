package com.zgq.wokaofree.data.realm.search;

import com.zgq.wokaofree.model.search.SearchHistory;

import java.util.List;

/**
 * Created by zgq on 2017/2/15.
 */

public interface ISearchHistoryProvider {
    List<SearchHistory> findRelative(String query, Integer limit);

    List<SearchHistory> findLastest(Integer limit);
}
