package com.zgq.wokao.module.study;

import com.zgq.wokao.R;
import com.zgq.wokao.injector.components.DaggerStudyComponent;
import com.zgq.wokao.injector.modules.StudyModule;
import com.zgq.wokao.module.base.BaseActivity;

public class StudyActivity extends BaseActivity<StudyContract.Presenter> implements StudyContract.View {
    @Override
    protected void daggerInject() {
        DaggerStudyComponent.builder()
                .applicationComponent(getAppComponent())
                .studyModule(new StudyModule())
                .build()
                .inject(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_study;
    }

    @Override
    protected void initViews() {

    }
}
