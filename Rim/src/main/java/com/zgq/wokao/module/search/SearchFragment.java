package com.zgq.wokao.module.search;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.common.base.Strings;
import com.zgq.wokao.R;
import com.zgq.wokao.adapter.SearchResultsListAdapter;
import com.zgq.wokao.injector.components.DaggerSearchComponent;
import com.zgq.wokao.injector.modules.SearchModule;
import com.zgq.wokao.module.base.BaseFragment;
import com.zgq.wokao.module.search.entity.HistorySuggestion;
import com.zgq.wokao.module.search.entity.Searchable;

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
    private SearchFragmentCallbacks mCallbacks;

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
        setupDrawer();
    }

    @Override
    public void showHistory(List<HistorySuggestion> histories) {
        mSearchView.swapSuggestions(histories);
    }

    @Override
    public void showSearchResult(List<Searchable> searchables) {
        mSearchResultsAdapter.replaceData(searchables);
    }

    public interface SearchFragmentCallbacks {
        void onAttachSearchViewBack(FloatingSearchView searchView);
        void onSelectItem(Searchable item);
    }


    protected void attachSearchViewBack(FloatingSearchView searchView) {
        if (mCallbacks != null) {
            //点击搜索框的返回按钮时执行操作
            mCallbacks.onAttachSearchViewBack(searchView);
        }
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
//                final HistorySuggestion suggestion = (HistorySuggestion) searchSuggestion;
//                mLastQuery = suggestion.getBody();
//                SearchAction.findPaperAndQst(mLastQuery, null, new SearchAction.OnFindPaperAndQstListener() {
//                    @Override
//                    public void onResults(List<Searchable> results) {
//                        mSearchResultsAdapter.swapData(results);
//                        SearchAction.clickSuggestion(suggestion);
//                    }
//                });
            }

            @Override
            public void onSearchAction(String query) {
//                mLastQuery = query;
//                SearchAction.findPaperAndQst(mLastQuery, null, new SearchAction.OnFindPaperAndQstListener() {
//                    @Override
//                    public void onResults(List<Searchable> results) {
//                        mSearchResultsAdapter.swapData(results);
//                        HistorySuggestion suggestion = new HistorySuggestion(mLastQuery);
//                        SearchAction.addSuggestion(suggestion);
//                    }
//                });
            }
        });

        mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {

//                //show suggestions when search bar gains focus (typically history suggestions)
//                SearchAction.getDefaultSuggestions(5, new SearchAction.OnFindSuggestionsListener() {
//                    @Override
//                    public void onResults(List<HistorySuggestion> results) {
//                        mSearchView.swapSuggestions(results);
//                    }
//                });
            }

            @Override
            public void onFocusCleared() {

                //set the title of the bar so that when focus is returned a new query begins
                mSearchView.setSearchBarTitle(mLastQuery);

            }
        });


        //handle menu clicks the same way as you would
        //in a regular activity
        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {


            }
        });

        //use this listener to listen to menu clicks when app:floatingSearch_leftAction="showHome"
        mSearchView.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() {
            @Override
            public void onHomeClicked() {

            }
        });

        /*
         * Here you have access to the left icon and the text of a given suggestion
         * item after as it is bound to the suggestion list. You can utilize this
         * callback to change some properties of the left icon and the text. For example, you
         * can load the left icon images using your favorite image loading library, or change text color.
         *
         *
         * Important:
         * Keep in mind that the suggestion list is a RecyclerView, so views are reused for different
         * items in the list.
         */
        mSearchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(View suggestionView, ImageView leftIcon,
                                         TextView textView, SearchSuggestion item, int itemPosition) {

            }

        });

        //listen for when suggestion list expands/shrinks in order to move down/up the
        //search results list
        mSearchView.setOnSuggestionsListHeightChanged(new FloatingSearchView.OnSuggestionsListHeightChanged() {
            @Override
            public void onSuggestionsListHeightChanged(float newHeight) {
                mSearchResultsList.setTranslationY(newHeight);
            }
        });
    }

    private void setupResultsList() {
        mSearchResultsAdapter = new SearchResultsListAdapter();
        mSearchResultsAdapter.setItemsOnClickListener(new SearchResultsListAdapter.OnItemClickListener() {
            @Override
            public void onClick(Searchable item) {
                mCallbacks.onSelectItem(item);
            }
        });
        mSearchResultsList.setAdapter(mSearchResultsAdapter);
        mSearchResultsList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public boolean onActivityBackPress() {
        //if mSearchView.setSearchFocused(false) causes the focused search
        //to close, then we don't want to close the activity. if mSearchView.setSearchFocused(false)
        //returns false, we know that the search was already closed so the call didn't change the focus
        //state and it makes sense to call supper onBackPressed() and close the activity
        if (!mSearchView.setSearchFocused(false)) {
            return false;
        }
        return true;
    }

    private void setupDrawer() {
        attachSearchViewBack(mSearchView);
    }
}
