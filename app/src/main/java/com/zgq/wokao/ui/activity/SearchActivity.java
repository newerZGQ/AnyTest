package com.zgq.wokao.ui.activity;

import android.app.Fragment;
import android.os.Bundle;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.zgq.wokao.R;
import com.zgq.wokao.ui.fragment.impl.SearchFragment;

/**
 * Created by zgq on 2017/2/11.
 */

public class SearchActivity extends BaseActivity implements SearchFragment.SearchFragmentCallbacks {
    private final String TAG = "SearchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Fragment searchFragment = new SearchFragment();
        showFragment(searchFragment);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getFragmentManager().findFragmentById(R.id.fragment_container);
        if (!(((SearchFragment)fragment).onActivityBackPress())) {
            super.onBackPressed();
        }
    }

    private void showFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
    }

    @Override
    public void onAttachSearchViewBack(FloatingSearchView searchView) {

    }
}
