package com.zgq.wokao.ui.activity;

import android.os.Bundle;

import com.zgq.wokao.R;
import com.zgq.wokao.Util.WXShare;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.ui.fragment.impl.QuestionsFragment;
import com.zgq.wokao.ui.fragment.impl.SettingsFragment;

/**
 * Created by zgq on 2017/10/23.
 */

public class QuestionsActivity extends BaseActivity implements QuestionsFragment.QuestionsFragmentListener {
    QuestionsFragment questionsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        String paperId = getIntent().getStringExtra("paperId");
        questionsFragment = QuestionsFragment.newInstance(paperId);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, questionsFragment)
                .commit();
    }

    @Override
    public void startFromQuestionFrag(String paperId, QuestionType type) {
        startStudy(paperId, type, 0, false);
    }
}
