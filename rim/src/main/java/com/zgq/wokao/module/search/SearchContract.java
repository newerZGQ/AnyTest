package com.zgq.wokao.module.search;

import com.zgq.wokao.entity.paper.question.QuestionType;
import com.zgq.wokao.module.IPresenter;
import com.zgq.wokao.module.IView;
import com.zgq.wokao.module.search.entity.HistorySuggestion;
import com.zgq.wokao.module.search.entity.Searchable;

import java.util.List;

public interface SearchContract {
    interface MainView extends IView<MainPresenter>{

    }

    interface MainPresenter extends IPresenter<MainView>{

    }

    interface SearchView extends IView<SearchPresenter>{
        void showHistory(List<HistorySuggestion> histories);
        void showSearchResult(List<Searchable> searchables);
        void toPaperInfo(String paperId);
        void toStudy(String paperId, QuestionType type, String questionId);
    }

    interface SearchPresenter extends IPresenter<SearchView>{
        void loadHistory(String query);
        void search(String query);
        void loadSearchable(Searchable searchable);
    }
}
