package com.zgq.wokaofree.action.search;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.zgq.wokaofree.Util.DateUtil;
import com.zgq.wokaofree.data.realm.Paper.impl.PaperDaoImpl;
import com.zgq.wokaofree.data.realm.search.SearchHistoryProvider;
import com.zgq.wokaofree.model.search.HistorySuggestion;
import com.zgq.wokaofree.model.search.SearchHistory;
import com.zgq.wokaofree.model.search.Searchable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zgq on 2017/2/15.
 */

public class SearchAction {
    public interface OnFindPaperAndQstListener {
        void onResults(List<Searchable> results);
    }

    public interface OnFindSuggestionsListener {
        void onResults(List<HistorySuggestion> results);
    }

    public interface OnGetDefaultSuggestionsListener {
        void OnResults(List<HistorySuggestion> results);
    }

    public static void findSuggesions(String query, int limit, OnFindSuggestionsListener listener) {
        List<SearchHistory> list = SearchHistoryProvider.getInstance().findRelative(query, 10);
        ArrayList<HistorySuggestion> results = new ArrayList<>();
        for (SearchHistory tmp : list) {
            HistorySuggestion suggestion = new HistorySuggestion(tmp.getContent());
            results.add(suggestion);
        }
        listener.onResults(results);
    }

    public static void findPaperAndQst(String query, Integer limit, OnFindPaperAndQstListener listener) {
        List<Searchable> list = PaperDaoImpl.getInstance().search(query);
        listener.onResults(list);
    }

    public static void getDefaultSuggestions(int limit, OnFindSuggestionsListener listener) {
        List<SearchHistory> list = SearchHistoryProvider.getInstance().findLastest(limit);
        ArrayList<HistorySuggestion> results = new ArrayList<>();
        for (SearchHistory tmp : list) {
            HistorySuggestion suggestion = new HistorySuggestion(tmp.getContent());
            results.add(suggestion);
        }
        listener.onResults(results);
    }

    public static void clickSuggestion(SearchSuggestion suggestion) {
        SearchHistoryProvider.getInstance().queryHistory(suggestion.getBody());
    }

    public static void addSuggestion(SearchSuggestion suggestion) {
        SearchHistory history = new SearchHistory();
        history.setContent(suggestion.getBody());
        history.setDate(DateUtil.getCurrentDate());
        SearchHistoryProvider.getInstance().save(history);
    }
}
