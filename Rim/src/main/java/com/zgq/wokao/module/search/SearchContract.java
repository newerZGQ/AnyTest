package com.zgq.wokao.module.search;

import com.zgq.wokao.module.IPresenter;
import com.zgq.wokao.module.IView;
import com.zgq.wokao.module.search.entity.HistorySuggestion;

import java.util.List;

public interface SearchContract {
    interface MainView extends IView<MainPresenter>{

    }

    interface MainPresenter extends IPresenter<MainView>{

    }

    interface SearchView extends IView<SearchPresenter>{
        void showHistory(List<HistorySuggestion> histories);
    }

    interface SearchPresenter extends IPresenter<SearchView>{
        void loadHistory();
    }
}
