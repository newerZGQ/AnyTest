package com.zgq.wokao.ui;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.adapter.BaseStudySystemAdapter;
import com.zgq.wokao.adapter.FillInQuestionAdapter;
import com.zgq.wokao.data.FillInQuestion;
import com.zgq.wokao.data.NormalExamPaper;


import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class AnswerStudyActivity extends AppCompatActivity implements View.OnClickListener {

    private int currentQuestionLabel = FILLINQUESTIONLABEL;

    private static final int FILLINQUESTIONLABEL = 1;
    private static final int TFQUESTIONLABEL = 2;
    private static final int SGLCHOQUESTIONLABEL = 3;
    private static final int MULTCHOQUESTIONLABEL = 4;
    private static final int DISCUSSQUESTIONLABEL = 5;

    //ViewPager
    @BindView(R.id.answer_study_pager)
    ViewPager viewPager;
    private PagerAdapter adapter;
    //显示答案按钮
    @BindView(R.id.show_answer)
    TextView showAnswerButton;
    //收藏按钮
    @BindView(R.id.set_star)
    TextView setStared;
    //底部menu
    @BindView(R.id.activity_study_bottom_menu)
    RelativeLayout bottomMenu;
    //弹出底部menu时用于遮挡ViewPager
    @BindView(R.id.background)
    LinearLayout background;
    //根View,//初始时将底部View放在屏幕底部，使用post是因为在onCreat中View没有绘制完全，getHeight返回0
    @BindView(R.id.root_view)
    FrameLayout rootView;

    private Realm realm = Realm.getDefaultInstance();
    private NormalExamPaper normalExamPaper;
    private RealmList<FillInQuestion> fillInQuestions = new RealmList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_study);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        currentQuestionLabel = intent.getIntExtra("QuestionLabel", FILLINQUESTIONLABEL);
        RealmResults<NormalExamPaper> papers = realm.where(NormalExamPaper.class).findAll();
        normalExamPaper = papers.get(0);
        fillInQuestions = normalExamPaper.getFillInQuestions();
    }

    private void initView() {
        rootView.post(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator.ofFloat(bottomMenu, "translationY", rootView.getHeight() - showAnswerButton.getHeight()).setDuration(0).start();
            }
        });
        viewPager.setAdapter(getCurrentAdapter());
        background.setVisibility(View.GONE);
        background.setOnClickListener(this);
        showAnswerButton.setOnClickListener(this);
        setStared.setOnClickListener(this);
    }

    private void showQuestionList() {
        background.setVisibility(View.VISIBLE);
        ObjectAnimator.ofFloat(bottomMenu, "translationY", rootView.getHeight() - bottomMenu.getHeight()).setDuration(500).start();
    }

    private void hideQuestionList() {
        background.setVisibility(View.GONE);
        ObjectAnimator.ofFloat(bottomMenu, "translationY", rootView.getHeight() - 200).setDuration(500).start();
    }

    private PagerAdapter getCurrentAdapter() {
        switch (currentQuestionLabel) {
            case FILLINQUESTIONLABEL:
                return new FillInQuestionAdapter(fillInQuestions, this);
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.background:
                hideQuestionList();
                break;
            case R.id.show_answer:
                ((BaseStudySystemAdapter) viewPager.getAdapter()).showCurrentAnswer();
                break;
            case R.id.set_star:
                showQuestionList();
                break;
        }
    }
}
