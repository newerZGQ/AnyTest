package com.zgq.wokao.module.study;

import com.zgq.wokao.R;
import com.zgq.wokao.module.base.BaseActivity;

/**
 * Created by zgq on 2017/12/3.
 */

public class StudyActivity extends BaseActivity<StudyContract.Presenter> implements StudyContract.View {
    @Override
    protected void daggerInject() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_study;
    }

    @Override
    protected void initViews() {

    }
}
