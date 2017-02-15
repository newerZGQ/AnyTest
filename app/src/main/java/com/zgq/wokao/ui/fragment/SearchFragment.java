package com.zgq.wokao.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.zgq.wokao.R;
import com.zgq.wokao.Util.FileUtil;
import com.zgq.wokao.adapter.SearchResultsListAdapter;
import com.zgq.wokao.data.realm.Paper.PaperDataProvider;
import com.zgq.wokao.model.search.HistorySuggestion;
import com.zgq.wokao.model.search.SearchQstItem;
import com.zgq.wokao.model.search.Searchable;
import com.zgq.wokao.parser.formater.impl.ImageFormater;
import com.zgq.wokao.service.search.SearchHelper;

import java.util.List;


/**
 * Created by zgq on 2017/2/11.
 */

public class SearchFragment extends BaseFragment {
    private final String TAG = "SearchFragment";

    public static final long FIND_SUGGESTION_SIMULATED_DELAY = 250;

    private FloatingSearchView mSearchView;

    private RecyclerView mSearchResultsList;
    private SearchResultsListAdapter mSearchResultsAdapter;

    private boolean mIsDarkSearchTheme = false;

    private String mLastQuery = "";

    private SearchFragmentCallbacks mCallbacks;

    private Button testBtn;
    private TextView testTv;

    public SearchFragment() {
        // Required empty public constructor
    }

    public interface SearchFragmentCallbacks {
        void onAttachSearchViewBack(FloatingSearchView searchView);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SearchFragmentCallbacks) {
            mCallbacks = (SearchFragmentCallbacks) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SearchFragmentCallbacks");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    protected void attachSearchViewBack(FloatingSearchView searchView){
        if(mCallbacks != null){
            //点击搜索框的返回按钮时执行操作
            mCallbacks.onAttachSearchViewBack(searchView);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSearchView = (FloatingSearchView) view.findViewById(R.id.floating_search_view);
        mSearchResultsList = (RecyclerView) view.findViewById(R.id.search_results_list);

        testBtn = (Button) view.findViewById(R.id.test_btn);
        testTv = (TextView) view.findViewById(R.id.test_tv);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ImageFormater imageFormater = ImageFormater.getInstance();
//                imageFormater.params(FileUtil.getOrInitAppStoragePath()+"/1.jpg");
//                imageFormater.getContent();
//                PaperDataProvider provider = PaperDataProvider.getInstance();
//                List<SearchQstItem> list1 = PaperDataProvider.getInstance().searchQstFromPaper("的",provider.getAllPaper().get(0));
//                List<Searchable> list = PaperDataProvider.getInstance().search("的");
//                for (SearchQstItem tmp : list1){
//                    Log.d("-------->>",tmp.getQst().getBody());
//                }
            }
        });

        setupFloatingSearch();
        setupResultsList();
        setupDrawer();
    }

    private void setupFloatingSearch() {
        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {

            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {
                if (!oldQuery.equals("") && newQuery.equals("")) {
                    mSearchView.clearSuggestions();
                } else {

                    //this shows the top left circular progress
                    //you can call it where ever you want, but
                    //it makes sense to do it when loading something in
                    //the background.
                    mSearchView.showProgress();

                    //simulates a query call to a data source
                    //with a new query.
                    SearchHelper.findSuggesions(newQuery, 10, new SearchHelper.OnFindSuggestionsListener() {
                        @Override
                        public void onResults(List<HistorySuggestion> results) {
                            mSearchView.swapSuggestions(results);
                            mSearchView.hideProgress();
                        }
                    });
                }
                Log.d(TAG, "onSearchTextChanged()");
            }
        });

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {
                HistorySuggestion suggestion = (HistorySuggestion)searchSuggestion;
                mLastQuery = suggestion.getBody();
                SearchHelper.findPaperAndQst(mLastQuery, null, new SearchHelper.OnFindPaperAndQstListener() {
                    @Override
                    public void onResults(List<Searchable> results) {
                        mSearchResultsAdapter.swapData(results);
                    }
                });
                Log.d(TAG, "onSuggestionClicked()");
            }

            @Override
            public void onSearchAction(String query) {
                mLastQuery = query;
                SearchHelper.findPaperAndQst(mLastQuery, null, new SearchHelper.OnFindPaperAndQstListener() {
                    @Override
                    public void onResults(List<Searchable> results) {
                        mSearchResultsAdapter.swapData(results);
                    }
                });
                Log.d(TAG, "onSearchAction()");
            }
        });

        mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {

                //show suggestions when search bar gains focus (typically history suggestions)

                Log.d(TAG, "onFocus()");
            }

            @Override
            public void onFocusCleared() {

                //set the title of the bar so that when focus is returned a new query begins
                mSearchView.setSearchBarTitle(mLastQuery);

                //you can also set setSearchText(...) to make keep the query there when not focused and when focus returns
                //mSearchView.setSearchText(searchSuggestion.getBody());

                Log.d(TAG, "onFocusCleared()");
            }
        });


        //handle menu clicks the same way as you would
        //in a regular activity
        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {

//                if (item.getItemId() == R.id.action_change_colors) {
//
//                    mIsDarkSearchTheme = true;
//
//                    //demonstrate setting colors for items
//                    mSearchView.setBackgroundColor(Color.parseColor("#787878"));
//                    mSearchView.setViewTextColor(Color.parseColor("#e9e9e9"));
//                    mSearchView.setHintTextColor(Color.parseColor("#e9e9e9"));
//                    mSearchView.setActionMenuOverflowColor(Color.parseColor("#e9e9e9"));
//                    mSearchView.setMenuItemIconColor(Color.parseColor("#e9e9e9"));
//                    mSearchView.setLeftActionIconColor(Color.parseColor("#e9e9e9"));
//                    mSearchView.setClearBtnColor(Color.parseColor("#e9e9e9"));
//                    mSearchView.setDividerColor(Color.parseColor("#BEBEBE"));
//                    mSearchView.setLeftActionIconColor(Color.parseColor("#e9e9e9"));
//                } else {
//
//                    //just print action
//                    Toast.makeText(getActivity().getApplicationContext(), item.getTitle(),
//                            Toast.LENGTH_SHORT).show();
//                }

            }
        });

        //use this listener to listen to menu clicks when app:floatingSearch_leftAction="showHome"
        mSearchView.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() {
            @Override
            public void onHomeClicked() {

                Log.d(TAG, "onHomeClicked()");
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
        mSearchResultsList.setAdapter(mSearchResultsAdapter);
        mSearchResultsList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
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
