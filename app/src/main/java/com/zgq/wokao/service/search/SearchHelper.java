package com.zgq.wokao.service.search;

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
//        ArrayList<Searchable> results = new ArrayList<>();
//        for (Searchable tmp : list){
//            if (tmp instanceof ExamPaperInfo){
//                SearchInfoItem infoItem = new SearchInfoItem();
//                infoItem.setInfo((ExamPaperInfo)tmp);
//                results.add(infoItem);
//            }
//            if (tmp instanceof Question){
//                Question question = (Question)tmp;
//                SearchQstItem qstItem = new SearchQstItem();
//                qstItem.setInfo(qstItem.getInfo());
//                qstItem.setQstId(question.getId());
//                qstItem.setQst(question);
//                qstItem.setQstType(question.getType());
//                results.add(qstItem);
//            }
//        }
        listener.onResults(list);
    }
}
