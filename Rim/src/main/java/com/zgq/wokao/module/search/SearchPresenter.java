package com.zgq.wokao.module.search;

import com.google.common.base.Strings;
import com.zgq.wokao.entity.search.SearchHistory;
import com.zgq.wokao.module.base.BasePresenter;
import com.zgq.wokao.module.search.entity.HistorySuggestion;
import com.zgq.wokao.repository.RimRepository;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.realm.RealmResults;

public class SearchPresenter extends BasePresenter<SearchContract.SearchView>
        implements SearchContract.SearchPresenter {
    RimRepository repository;

    @Inject
    public SearchPresenter(RimRepository repository){
        this.repository = repository;
    }

    @Override
    public void loadHistory(String query) {
        ArrayList<HistorySuggestion> suggestions = new ArrayList<>();
        if (Strings.isNullOrEmpty(query)){
            repository.getLastestSearchHistory(10)
                    .flatMap(new Function<SearchHistory, Publisher<HistorySuggestion>>() {
                        @Override
                        public Publisher<HistorySuggestion> apply(SearchHistory searchHistory) throws Exception {
                            return Flowable.just(new HistorySuggestion(searchHistory.getContent()));
                        }
                    })
                    .subscribe(suggestion -> suggestions.add(suggestion));
        }else{

        }
        view.showHistory(suggestions);
    }
}
