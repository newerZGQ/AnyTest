package com.zgq.wokao.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.zgq.wokao.R;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.search.SearchInfoItem;
import com.zgq.wokao.model.search.SearchQstItem;
import com.zgq.wokao.model.search.Searchable;
import com.zgq.wokao.ui.fragment.impl.SearchFragment;
import com.zgq.wokao.ui.presenter.ISearchPresenter;
import com.zgq.wokao.ui.presenter.impl.SearchPresenter;

/**
 * Created by zgq on 2017/2/11.
 */

public class SearchActivity extends BaseActivity implements SearchFragment.SearchFragmentCallbacks {
    private final String TAG = "SearchActivity";

    private ISearchPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        presenter = new SearchPresenter();
        Fragment searchFragment = new SearchFragment();
        showFragment(searchFragment);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (!(((SearchFragment) fragment).onActivityBackPress())) {
            finish();
            super.onBackPressed();
        }
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    @Override
    public void onAttachSearchViewBack(FloatingSearchView searchView) {

    }

    @Override
    public void onSelectItem(Searchable item) {
        if (item instanceof SearchInfoItem) {
            String paperId = ((SearchInfoItem) item).getInfo().getId();
            startStudy(paperId, presenter.getLastStudyType(paperId), presenter.getLastStudyIndex(paperId), true);

        }
        if (item instanceof SearchQstItem) {
            String paperId = ((SearchQstItem) item).getInfo().getId();
            startStudy(paperId, ((SearchQstItem) item).getQstType(), ((SearchQstItem) item).getQstId()-1, true);
        }
    }
}
