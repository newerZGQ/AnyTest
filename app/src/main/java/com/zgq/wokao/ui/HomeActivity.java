package com.zgq.wokao.ui;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.zgq.wokao.R;
import com.zgq.wokao.ui.fragment.HomeFragment;

public class HomeActivity extends BaseActivity implements HomeFragment.OnFragmentInteractionListener{

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
}
