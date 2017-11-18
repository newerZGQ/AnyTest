package com.zgq.wokaofree.ui.activity;

import android.os.Bundle;

import com.zgq.wokaofree.R;
import com.zgq.wokaofree.ui.fragment.impl.LearningFragment;

/**
 * Created by zhangguoqiang on 2017/10/21.
 */

public class LearningActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new LearningFragment())
                .commit();
    }
}
