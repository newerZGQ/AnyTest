package com.zgq.wokao.module.study;

import com.zgq.wokao.R;
import com.zgq.wokao.injector.components.DaggerStudyComponent;
import com.zgq.wokao.injector.modules.StudyModule;
import com.zgq.wokao.module.base.BaseActivity;

import javax.inject.Inject;

public class StudyActivity extends BaseActivity<StudyContract.MainPresenter> implements StudyContract.MainView {

    @Inject
    LearningFragment learningFragment;

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
        addFragment(R.id.fragment_container,learningFragment);
    }
}
