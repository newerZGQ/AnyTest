package com.zgq.wokao.ui;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;

import com.zgq.wokao.R;
import com.zgq.wokao.ui.fragment.impl.HomeFragment;

public class HomeActivity extends BaseActivity implements HomeFragment.OnHomeFragmentListener {

    public static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        HomeFragment homeFragment = HomeFragment.newInstance("","");
        showFragment(homeFragment);
    }

    private void showFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void goSearch() {
        openActivity(SearchActivity.class);
    }

    @Override
    public void goQuestionsList(String paperId) {
        Bundle bundle = new Bundle();
        bundle.putString("paperId",paperId);
        openActivity(QuestionsListActivity.class,bundle);
    }
}
