package com.zgq.wokao.module.question;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.adapter.QuestionsInfoAdapter;
import com.zgq.wokao.entity.paper.info.ExamPaperInfo;
import com.zgq.wokao.injector.components.DaggerQuestionComponent;
import com.zgq.wokao.injector.modules.QuestionsModule;
import com.zgq.wokao.module.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class QuestionsActivity extends BaseActivity<QuestionContract.Presenter>
        implements QuestionContract.View {

    @BindView(R.id.questions_list)
    RecyclerView questions;
    @BindView(R.id.toolbar_title)
    TextView back;

    @Override
    public void showQuestions(List<QuestionsInfoAdapter.QuestionsInfo> questions,
                              ExamPaperInfo paperInfo) {
        questionsInfoAdapter.replaceData(questions, paperInfo);
    }

    @Override
    protected void daggerInject() {
        DaggerQuestionComponent.builder()
                .applicationComponent(getAppComponent())
                .questionsModule(new QuestionsModule())
                .build()
                .inject(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_questions;
    }

    @Override
    protected void initViews() {
        initQuestionList();
        back.setOnClickListener(view -> {

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadQuestions(getIntent().getStringExtra("paperId"));
    }

    private void initQuestionList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                OrientationHelper.VERTICAL, false);
        questions.setLayoutManager(layoutManager);
        LinearLayout.LayoutParams lp = new LinearLayout.
                LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        questions.setLayoutParams(lp);
        questions.setAdapter(questionsInfoAdapter);
    }

    private QuestionsInfoAdapter questionsInfoAdapter =
            new QuestionsInfoAdapter(new ArrayList<>(0), null, position -> {

                    });
}
