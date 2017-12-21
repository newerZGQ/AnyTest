package com.zgq.wokao.module.search;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.common.base.Strings;
import com.zgq.wokao.R;
import com.zgq.wokao.adapter.SearchResultsListAdapter;
import com.zgq.wokao.entity.paper.question.QuestionType;
import com.zgq.wokao.injector.components.DaggerSearchComponent;
import com.zgq.wokao.injector.modules.SearchModule;
import com.zgq.wokao.module.base.BaseFragment;
import com.zgq.wokao.module.question.QuestionsActivity;
import com.zgq.wokao.module.search.entity.HistorySuggestion;
import com.zgq.wokao.module.search.entity.SearchInfoItem;
import com.zgq.wokao.module.search.entity.Searchable;
import com.zgq.wokao.module.study.StudyActivity;

import java.util.List;

import butterknife.BindView;


public class SearchFragment extends BaseFragment<SearchContract.SearchPresenter>
        implements SearchContract.SearchView {

    private final String TAG = SearchFragment.class.getSimpleName();

    @BindView(R.id.floating_search_view)
    FloatingSearchView mSearchView;

    @BindView(R.id.search_results_list)
    RecyclerView mSearchResultsList;

    private SearchResultsListAdapter mSearchResultsAdapter;
    private String mLastQuery = "";

    public SearchFragment() {

    }

    @Override
    protected void daggerInject() {
        DaggerSearchComponent.builder()
                .applicationComponent(getAppComponent())
                .searchModule(new SearchModule())
                .build()
                .inject(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initViews() {
        setupFloatingSearch();
        setupResultsList();
    }

    @Override
    public void showHistory(List<HistorySuggestion> histories) {
        mSearchView.swapSuggestions(histories);
    }

    @Override
    public void showSearchResult(List<Searchable> searchables) {
        mSearchResultsAdapter.replaceData(searchables);
    }

    @Override
    public void toPaperInfo(String paperId) {
        Intent intent = new Intent();
        intent.putExtra("paperId", paperId);
        intent.setClass(getAppComponent().getContext(), QuestionsActivity.class);
        startActivity(intent);
    }

    @Override
    public void toStudy(String paperId, QuestionType type, String questionId) {
        Intent intent = new Intent();
        intent.putExtra("paperId", paperId);
        intent.putExtra("questionType", (Parcelable) type);
        intent.putExtra("questionId", questionId);
        intent.setClass(getAppComponent().getContext(), StudyActivity.class);
        startActivity(intent);
    }

    private void setupFloatingSearch() {
        mSearchView.setOnQueryChangeListener((oldQuery, newQuery) -> {
            if (!Strings.isNullOrEmpty(oldQuery) && Strings.isNullOrEmpty(newQuery)){
                mSearchView.clearSuggestions();
            }else{
                mSearchView.showProgress();
                presenter.loadHistory(newQuery);
                mSearchView.hideProgress();
            }
        });

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {
                final HistorySuggestion suggestion = (HistorySuggestion) searchSuggestion;
                mLastQuery = suggestion.getBody();
                mSearchView.showProgress();
                presenter.search(mLastQuery);
                mSearchView.hideProgress();
            }

            @Override
            public void onSearchAction(String query) {
                mLastQuery = query;
                mSearchView.showProgress();
                presenter.search(mLastQuery);
                mSearchView.hideProgress();
            }
        });

        mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                presenter.loadHistory("");
            }

            @Override
            public void onFocusCleared() {
                mSearchView.setSearchBarTitle(mLastQuery);
            }
        });

        mSearchView.setOnSuggestionsListHeightChanged(newHeight ->
                mSearchResultsList.setTranslationY(newHeight));
    }

    private void setupResultsList() {
        mSearchResultsAdapter = new SearchResultsListAdapter();
        mSearchResultsAdapter.setItemsOnClickListener(item -> {
            presenter.loadSearchable(item);
        });
        mSearchResultsList.setAdapter(mSearchResultsAdapter);
        mSearchResultsList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    protected void startStudy(String paperId, QuestionType type, int qstNum) {
        Intent intent = new Intent();
        intent.setClass(getContext(), StudyActivity.class);
        intent.putExtra("paperId", paperId);
        intent.putExtra("questionType", (Parcelable) type);
        intent.putExtra("qstNum", qstNum);
        startActivity(intent);
    }

    public boolean onActivityBackPress() {
        if (!mSearchView.setSearchFocused(false)) {
            return false;
        }
        return true;
    }
}
