package com.zgq.wokao.module.study;

import android.content.Intent;

import com.zgq.wokao.R;
import com.zgq.wokao.entity.paper.question.QuestionType;
import com.zgq.wokao.injector.components.DaggerStudyComponent;
import com.zgq.wokao.injector.modules.StudyModule;
import com.zgq.wokao.module.base.BaseActivity;
import com.zgq.wokao.module.study.entity.StudyParams;

import javax.inject.Inject;

public class StudyActivity extends BaseActivity<StudyContract.MainPresenter> implements StudyContract.MainView {

    @Inject
    LearningFragment learningFragment;

    @Override
    protected void daggerInject() {
        Intent intent = getIntent();
        String paperId = intent.getStringExtra("paperId");
        QuestionType type = intent.getParcelableExtra("questionType");
        String questionId = intent.getStringExtra("questionId");
        DaggerStudyComponent.builder()
                .applicationComponent(getAppComponent())
                .studyModule(new StudyModule(new StudyParams(paperId, type, questionId)))
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
