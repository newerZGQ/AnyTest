package com.zgq.wokao.service.search;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.zgq.wokao.Util.DateUtil;
import com.zgq.wokao.data.realm.Paper.PaperDataProvider;
import com.zgq.wokao.data.realm.search.SearchHistoryProvider;
import com.zgq.wokao.model.paper.ExamPaperInfo;
import com.zgq.wokao.model.paper.Question;
import com.zgq.wokao.model.search.HistorySuggestion;
import com.zgq.wokao.model.search.SearchHistory;
import com.zgq.wokao.model.search.SearchInfoItem;
import com.zgq.wokao.model.search.SearchQstItem;
import com.zgq.wokao.model.search.Searchable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zgq on 2017/2/15.
 */

public class SearchHelper {
    public interface OnFindPaperAndQstListener {
        void onResults(List<Searchable> results);
    }

    public interface OnFindSuggestionsListener {
        void onResults(List<HistorySuggestion> results);
    }

    public interface OnGetDefaultSuggestionsListener{
        void OnResults(List<HistorySuggestion> results);
    }
    public static void findSuggesions(String query, int limit, OnFindSuggestionsListener listener){
        List<SearchHistory> list = SearchHistoryProvider.getInstance().findRelative(query,10);
        ArrayList<HistorySuggestion> results = new ArrayList<>();
        for (SearchHistory tmp : list){
            HistorySuggestion suggestion = new HistorySuggestion(tmp.getContent());
            results.add(suggestion);
        }
        listener.onResults(results);
    }
    public static void findPaperAndQst(String query, Integer limit, OnFindPaperAndQstListener listener){
        List<Searchable> list = PaperDataProvider.getInstance().search(query);
        listener.onResults(list);
    }

    public static void getDefaultSuggestions(int limit, OnFindSuggestionsListener listener){
        List<SearchHistory> list = SearchHistoryProvider.getInstance().findLastest(limit);
        ArrayList<HistorySuggestion> results = new ArrayList<>();
        for (SearchHistory tmp : list){
            HistorySuggestion suggestion = new HistorySuggestion(tmp.getContent());
            results.add(suggestion);
        }
        listener.onResults(results);
    }

    public static void clickSuggestion(SearchSuggestion suggestion){
        SearchHistoryProvider.getInstance().queryHistory(suggestion.getBody());
    }

    public static void addSuggestion(SearchSuggestion suggestion){
        SearchHistory history = new SearchHistory();
        history.setContent(suggestion.getBody());
        history.setDate(DateUtil.getCurrentDate());
        SearchHistoryProvider.getInstance().save(history);
    }
}
