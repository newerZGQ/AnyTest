package com.zgq.wokao.ui.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zgq.wokao.R;
import com.zgq.wokao.action.paper.impl.PaperAction;
import com.zgq.wokao.model.paper.NormalExamPaper;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.paper.question.answer.IAnswer;
import com.zgq.wokao.model.paper.question.impl.DiscussQuestion;
import com.zgq.wokao.model.paper.question.impl.FillInQuestion;
import com.zgq.wokao.model.paper.question.impl.MultChoQuestion;
import com.zgq.wokao.model.paper.question.impl.SglChoQuestion;
import com.zgq.wokao.model.paper.question.impl.TFQuestion;
import com.zgq.wokao.model.paper.question.IQuestion;
import com.zgq.wokao.ui.adapter.BaseStudySystemAdapter;
import com.zgq.wokao.ui.adapter.BaseViewPagerAdapter;
import com.zgq.wokao.ui.adapter.DiscussQuestionAdapter;
import com.zgq.wokao.ui.adapter.FillInQuestionAdapter;
import com.zgq.wokao.ui.adapter.MultChoQuestionAdapter;
import com.zgq.wokao.ui.adapter.SglChoQuestionAdapter;
import com.zgq.wokao.ui.adapter.TFQuestionAdapter;
import com.zgq.wokao.model.paper.question.answer.MyAnswer;
import com.zgq.wokao.ui.presenter.impl.AnswerStudyPresenter;
import com.zgq.wokao.ui.view.IStudyAnswerView;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class AnswerStudyActivity extends BaseActivity implements IStudyAnswerView, View.OnClickListener {

    private static final String TAG = AnswerStudyActivity.class.getSimpleName();

    //返回
    @BindView(R.id.toolbar_back)
    TextView toolbarBack;

    //编辑
    @BindView(R.id.toolbar_edit)
    TextView toolbarEdit;

    //保存
    @BindView(R.id.toolbar_done)
    TextView toolbarDone;

    //切换模式
    @BindView(R.id.all_question)
    TextView studyMode;

    @BindView(R.id.stared_question)
    TextView remebMode;

    //ViewPager
    @BindView(R.id.answer_study_pager)
    ViewPager viewPager;

    //显示答案按钮
    @BindView(R.id.show_answer)
    LinearLayout showAnswerButton;

    @BindView(R.id.answer_label)
    TextView answerLabel;

    //收藏按钮
    @BindView(R.id.set_star)
    LinearLayout setStared;
    @BindView(R.id.star_label)
    TextView starLabel;

    //列表按钮
    @BindView(R.id.question_list)
    LinearLayout questionListTv;

    //题号列表
    @BindView(R.id.question_num_list)
    GridView questionListGv;

    //学习记录
    @BindView(R.id.study_info)
    TextView studyInfo;

    //底部menu
    @BindView(R.id.activity_study_bottom_menu)
    RelativeLayout bottomMenu;

    //弹出底部menu时用于遮挡ViewPager
    @BindView(R.id.background)
    LinearLayout background;

    //根View,//初始时将底部View放在屏幕底部，使用post是因为在onCreat中View没有绘制完全，getHeight返回0
    @BindView(R.id.root_view)
    RelativeLayout rootView;

    //编辑问题的layout
    private View editLayout;

    private ViewStub editStub;

    private Realm realm = Realm.getDefaultInstance();
    private NormalExamPaper normalExamPaper;

    private QuestionType currentQuestionType = QuestionType.FILLIN;

    private ArrayList<IQuestion> currentAllQuestions = new ArrayList<>();
    private ArrayList<Boolean> allAnsweredList = new ArrayList<>();
    private BaseViewPagerAdapter currentAllQstAdapter;
    private ArrayList<IAnswer> currentAllMyAnswer = new ArrayList<>();

    private ArrayList<IQuestion> currentStarQuestions = new ArrayList<>();
    private ArrayList<Boolean> starAnsweredList = new ArrayList<>();
    private BaseViewPagerAdapter currentStarQstAdapter;
    private ArrayList<IAnswer> currentStarMyAnswer = new ArrayList<>();

    private AnswerStudyPresenter presenter;

    private BaseViewPagerAdapter.OnStudiedListener onStudiedListener;

    private final int ALLQUESTIONMODE = 1;
    private final int STARQUESTIONMODE = 2;

    private int currentMode = ALLQUESTIONMODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int mScreenWidth = getWindowManager().getDefaultDisplay().getWidth();
        int mScreenHeight = getWindowManager().getDefaultDisplay().getHeight();
        View view = getLayoutInflater().inflate(R.layout.activity_answer_study, null);
        setContentView(view, new ViewGroup.LayoutParams(mScreenWidth, mScreenHeight));

        //setContentView(R.layout.activity_answer_study);
        ButterKnife.bind(this);
        initData();
        initView();
        presenter = new AnswerStudyPresenter(this);
    }



    public void onResume() {
        super.onResume();
        setQuestionStudyInfo(0);
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void initData() {
        initPaperData();
        initStudiedListener();
        initCurrentQuestionList();
        initAnsweredList();
        initCurrentStaredQuestion();
        initStarAnsweredList();
        initCurrentAllQstAdapter();
        initCurrentStarQstAdapter();
        if (isNeedAnswer()) {
            initCurrentMyAllAnswer();
            initCurrentMyStarAnswer();
        }
        PaperAction.getInstance().setLastStudyDate(normalExamPaper);
    }

    private void initPaperData() {
        Intent intent = getIntent();
        String paperId = intent.getStringExtra("paperId");
        currentQuestionType = intent.getParcelableExtra("qstType");
        normalExamPaper = (NormalExamPaper) PaperAction.getInstance().queryById(paperId);
    }

    //初始化监听器
    private void initStudiedListener() {
        onStudiedListener = new BaseViewPagerAdapter.OnStudiedListener() {
            @Override
            public void onFalse() {
                setQuestionStudyInfo(viewPager.getCurrentItem());
            }

            @Override
            public void onCorrect() {
                setQuestionStudyInfo(viewPager.getCurrentItem());
            }
        };
    }

    //初始化当前的问题
    private void initCurrentQuestionList() {
        currentAllQuestions.clear();
        switch (currentQuestionType) {
            case FILLIN:
                for (FillInQuestion question : normalExamPaper.getFillInQuestions()) {
                    currentAllQuestions.add(question);
                }
                break;
            case TF:
                for (TFQuestion question : normalExamPaper.getTfQuestions()) {
                    currentAllQuestions.add(question);
                }
                break;
            case SINGLECHOOSE:
                for (SglChoQuestion question : normalExamPaper.getSglChoQuestions()) {
                    currentAllQuestions.add(question);
                }
                break;
            case MUTTICHOOSE:
                for (MultChoQuestion question : normalExamPaper.getMultChoQuestions()) {
                    currentAllQuestions.add(question);
                }
                break;
            case DISCUSS:
                for (DiscussQuestion question : normalExamPaper.getDiscussQuestions()) {
                    currentAllQuestions.add(question);
                }
                break;
            default:
                break;
        }
    }

    //初始化是否回答过的记录链表
    private void initAnsweredList() {
        allAnsweredList.clear();
        for (int i = 0; i < currentAllQuestions.size(); i++) {
            allAnsweredList.add(false);
        }
    }

    //初始化当前的已经加星的问题链表
    private void initCurrentStaredQuestion() {
        currentStarQuestions.clear();
        for (IQuestion question : currentAllQuestions) {
            if (question.getInfo().isStared()) currentStarQuestions.add(question);
        }
    }

    private void initStarAnsweredList() {
        starAnsweredList.clear();
        for (IQuestion question : currentAllQuestions) {
            int i = currentAllQuestions.indexOf(question);
            if (question.getInfo().isStared())
                starAnsweredList.add(allAnsweredList.get(i));
        }
    }

    private void initCurrentMyAllAnswer() {
        currentAllMyAnswer.clear();
        for (int i = 0; i < currentAllQuestions.size(); i++) {
            MyAnswer answer = new MyAnswer();
            currentAllMyAnswer.add(answer);
        }
    }

    private void initCurrentMyStarAnswer() {
        currentStarMyAnswer.clear();
        for (IQuestion question : currentAllQuestions) {
            int i = currentAllQuestions.indexOf(question);
            if (question.getInfo().isStared())
                currentStarMyAnswer.add(currentAllMyAnswer.get(i));
        }
    }

    private boolean isNeedAnswer() {
        if (currentQuestionType == QuestionType.TF || currentQuestionType == QuestionType.SINGLECHOOSE ||
                currentQuestionType == QuestionType.MUTTICHOOSE) {
            return true;
        }
        return false;
    }


    private void initView() {
        initToolbar();
        viewPager.setAdapter(currentAllQstAdapter);
        background.setVisibility(View.GONE);
        background.setOnClickListener(this);
        showAnswerButton.setOnClickListener(this);
        if (currentQuestionType == QuestionType.TF || currentQuestionType == QuestionType.SINGLECHOOSE) {
            showAnswerButton.setVisibility(View.GONE);
        }
        setStared.setOnClickListener(this);
        questionListTv.setOnClickListener(this);
        initQstPstGridView();
        rootView.post(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator.ofFloat(bottomMenu, "translationY", bottomMenu.getHeight() - setStared.getHeight()).
                        setDuration(0).start();
                viewPager.setCurrentItem(getIntent().getIntExtra("qstNum", 0));
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                final int p = position;
                upDateBottomMenu(position);
                setQuestionStudyInfo(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        upDateBottomMenu(getCurrentQstAdapter().getCurrentPosition());
    }

    private void setQuestionStudyInfo(int position) {
        if (currentMode == ALLQUESTIONMODE) {
            String info = getResources().getString(R.string.correct) + currentAllQuestions
                    .get(position)
                    .getRecord()
                    .getCorrectNumber() + "/"
                    + currentAllQuestions.get(position).getRecord().getStudyNumber();
            studyInfo.setText(info);
        } else {
            String info = getResources().getString(R.string.wrong) + currentStarQuestions
                    .get(position)
                    .getRecord()
                    .getCorrectNumber() + "/"
                    + currentAllQuestions.get(position).getRecord().getStudyNumber();
            studyInfo.setText(info);
        }
    }

    private void initQstPstGridView() {
        questionListGv.setAdapter(new QuestionPositionAdapter(allAnsweredList, this.getApplicationContext()));
        questionListGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewPager.setCurrentItem(position);
                hideQuestionList();
            }
        });
    }

    private void initToolbar() {
        studyMode.setOnClickListener(this);
        remebMode.setOnClickListener(this);
        toolbarBack.setOnClickListener(this);
        toolbarEdit.setOnClickListener(this);
        toolbarDone.setOnClickListener(this);
    }


    private void upDateBottomMenu(int position) {
        switch (getCurrentMode()) {
            case ALLQUESTIONMODE:
                if (currentAllQuestions.get(position).getInfo().isStared()) {
                    starLabel.setBackground(getResources().getDrawable(R.drawable.active_star));
                } else {
                    starLabel.setBackground(getResources().getDrawable(R.drawable.inactive_star));
                }
                if (allAnsweredList.get(position)) {
                    answerLabel.setBackground(getResources().getDrawable(R.drawable.active_light));
                } else {
                    answerLabel.setBackground(getResources().getDrawable(R.drawable.inactive_light));
                }
                break;
            case STARQUESTIONMODE:
                if (currentStarQuestions.size() == 0) return;
                if (currentStarQuestions.get(position).getInfo().isStared()) {
                    starLabel.setBackground(getResources().getDrawable(R.drawable.active_star));
                } else {
                    starLabel.setBackground(getResources().getDrawable(R.drawable.inactive_star));
                }
                if (starAnsweredList.get(position)) {
                    answerLabel.setBackground(getResources().getDrawable(R.drawable.active_light));
                } else {
                    answerLabel.setBackground(getResources().getDrawable(R.drawable.inactive_light));
                }
                break;
        }

    }

    public int getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(int mode) {
        currentMode = mode;
    }

    private BaseStudySystemAdapter getCurrentQstAdapter() {
        return ((BaseStudySystemAdapter) viewPager.getAdapter());
    }

    private void setStar() {
        if (currentMode == STARQUESTIONMODE && currentStarQuestions.size() == 0) return;
        final int position = getCurrentQstAdapter().getCurrentPosition();
        switch (getCurrentMode()) {
            case ALLQUESTIONMODE:
                if (currentAllQuestions.get(position).getInfo().isStared()) {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            currentAllQuestions.get(position).getInfo().setStared(false);
                        }
                    });
                    starLabel.setBackground(getResources().getDrawable(R.drawable.inactive_star));
                } else {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            currentAllQuestions.get(position).getInfo().setStared(true);
                        }
                    });
                    starLabel.setBackground(getResources().getDrawable(R.drawable.active_star));
                }
                break;
            case STARQUESTIONMODE:
                if (currentStarQuestions.get(position).getInfo().isStared()) {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            currentStarQuestions.get(position).getInfo().setStared(false);
                        }
                    });
                    starLabel.setBackground(getResources().getDrawable(R.drawable.inactive_star));
                } else {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            currentStarQuestions.get(position).getInfo().setStared(true);
                        }
                    });
                    starLabel.setBackground(getResources().getDrawable(R.drawable.active_star));
                }
                break;
        }
    }

    private void showCurrentAnswer() {
        if (currentMode == STARQUESTIONMODE && currentStarQuestions.size() == 0) return;
        switch (getCurrentMode()) {
            case ALLQUESTIONMODE:
                getCurrentQstAdapter().showCurrentAnswer();
                allAnsweredList.set(getCurrentQstAdapter().getCurrentPosition(), true);
                answerLabel.setBackground(getResources().getDrawable(R.drawable.active_light));
                break;
            case STARQUESTIONMODE:
                getCurrentQstAdapter().showCurrentAnswer();
                starAnsweredList.set(getCurrentQstAdapter().getCurrentPosition(), true);
                answerLabel.setBackground(getResources().getDrawable(R.drawable.active_light));
        }


    }

    private void showQuestionList() {
        background.setVisibility(View.VISIBLE);
        ObjectAnimator.ofFloat(bottomMenu, "translationY", 0).setDuration(300).start();
    }

    private void hideQuestionList() {
        background.setVisibility(View.GONE);
        ObjectAnimator.ofFloat(bottomMenu, "translationY", bottomMenu.getHeight() - setStared.getHeight()).setDuration(300).start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        updateLastStudyPosition();
        finish();
    }

    private void updateLastStudyPosition() {
        int position = 0;
        if (currentMode == ALLQUESTIONMODE) {
            position = currentAllQstAdapter.getLastPosition();
        } else if (currentMode == STARQUESTIONMODE) {
            position = currentStarQstAdapter.getLastPosition();
        }
        PaperAction.getInstance().updateLastStudyPosition(normalExamPaper, currentQuestionType, position);

    }

    private PagerAdapter initCurrentAllQstAdapter() {
        switch (currentQuestionType) {
            case FILLIN:
                currentAllQstAdapter = new FillInQuestionAdapter(currentAllQuestions, allAnsweredList, this);
                break;
            case DISCUSS:
                currentAllQstAdapter = new DiscussQuestionAdapter(currentAllQuestions, allAnsweredList, this);
                break;
            case TF:
                currentAllQstAdapter = new TFQuestionAdapter(currentAllQuestions, allAnsweredList, currentAllMyAnswer, this);
                break;
            case SINGLECHOOSE:
                currentAllQstAdapter = new SglChoQuestionAdapter(currentAllQuestions, allAnsweredList, currentAllMyAnswer, this);
                break;
            case MUTTICHOOSE:
                currentAllQstAdapter = new MultChoQuestionAdapter(currentAllQuestions, allAnsweredList, currentAllMyAnswer, this);
                break;
            default:
                break;
        }
        currentAllQstAdapter.setPaperId(normalExamPaper.getPaperInfo().getId());
        currentAllQstAdapter.setStudiedListener(onStudiedListener);
        return currentAllQstAdapter;
    }

    private PagerAdapter initCurrentStarQstAdapter() {

        switch (currentQuestionType) {
            case FILLIN:
                currentStarQstAdapter = new FillInQuestionAdapter(currentStarQuestions, starAnsweredList, this);
                break;
            case DISCUSS:
                currentStarQstAdapter = new DiscussQuestionAdapter(currentStarQuestions, starAnsweredList, this);
                break;
            case TF:
                currentStarQstAdapter = new TFQuestionAdapter(currentStarQuestions, starAnsweredList, currentStarMyAnswer, this);
                break;
            case SINGLECHOOSE:
                currentStarQstAdapter = new SglChoQuestionAdapter(currentStarQuestions, starAnsweredList, currentStarMyAnswer, this);
                break;
            case MUTTICHOOSE:
                currentStarQstAdapter = new MultChoQuestionAdapter(currentStarQuestions, starAnsweredList, currentStarMyAnswer, this);
                break;
        }
        currentStarQstAdapter.setPaperId(normalExamPaper.getPaperInfo().getId());
        currentStarQstAdapter.setStudiedListener(onStudiedListener);
        return currentStarQstAdapter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.background:
                hideQuestionList();
                break;
            case R.id.show_answer:
                showCurrentAnswer();
                break;
            case R.id.set_star:
                setStar();
                break;
            case R.id.question_list:
                ((QuestionPositionAdapter) (questionListGv.getAdapter())).notifyDataSetChanged();
                showQuestionList();
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.all_question:
                studyMode.setBackground(getResources().getDrawable(R.drawable.activity_asstd_left_white_halfrect_circle));
                studyMode.setTextColor(getResources().getColor(R.color.colorTeal));
                remebMode.setBackground(getResources().getDrawable(R.drawable.activity_asstd_right_teal_halfrect_circle));
                remebMode.setTextColor(getResources().getColor(R.color.colorWhite));

                initCurrentQuestionList();
                initCurrentAllQstAdapter();
                setCurrentMode(ALLQUESTIONMODE);
                viewPager.setAdapter(currentAllQstAdapter);
                currentAllQstAdapter.notifyDataSetChanged();
                upDateBottomMenu(getCurrentQstAdapter().getCurrentPosition());
                break;
            case R.id.stared_question:
                remebMode.setBackground(getResources().getDrawable(R.drawable.activity_asstd_right_white_halfrect_circle));
                remebMode.setTextColor(getResources().getColor(R.color.colorTeal));
                studyMode.setBackground(getResources().getDrawable(R.drawable.activity_asstd_left_teal_halfrect_circle));
                studyMode.setTextColor(getResources().getColor(R.color.colorWhite));

                initCurrentStaredQuestion();
                initStarAnsweredList();
                if (isNeedAnswer()) initCurrentMyStarAnswer();
                initCurrentStarQstAdapter();

                setCurrentMode(STARQUESTIONMODE);

                viewPager.setAdapter(currentStarQstAdapter);
                currentStarQstAdapter.notifyDataSetChanged();
                upDateBottomMenu(getCurrentQstAdapter().getCurrentPosition());
                break;
            case R.id.toolbar_edit:
                startEditMode();
                break;
            case R.id.toolbar_done:

                stopEditMode();
                break;
        }
    }

    @Override
    public void startEditMode() {
        toolbarEdit.setVisibility(View.GONE);
        toolbarDone.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.INVISIBLE);
        bottomMenu.setVisibility(View.INVISIBLE);

        switch (currentQuestionType) {
            case FILLIN:
                if (editLayout == null) {
                    editStub = (ViewStub) findViewById(R.id.edit_fillin);
                    editStub.inflate();
                    editLayout = findViewById(R.id.edit_fillin_layout);
                }
                editLayout.setVisibility(View.VISIBLE);
                EditText body = (EditText) editLayout.findViewById(R.id.edit_fillin_body);
                EditText answer = (EditText) editLayout.findViewById(R.id.edit_fillin_answer);
                FillInQuestion question = null;
                int position = viewPager.getCurrentItem();
                if (currentMode == ALLQUESTIONMODE) {
                    question = (FillInQuestion) currentAllQuestions.get(position);
                } else if (currentMode == STARQUESTIONMODE) {
                    question = (FillInQuestion) currentStarQuestions.get(position);
                }
                if (question == null) return;
                body.setText(question.getBody().getContent());
                answer.setText(question.getAnswer().getContent());
                break;
            case TF:
                break;

        }
    }

    @Override
    public void stopEditMode() {
        toolbarDone.setVisibility(View.GONE);
        toolbarEdit.setVisibility(View.VISIBLE);
        editLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.VISIBLE);
        bottomMenu.setVisibility(View.VISIBLE);
    }

    private IQuestion getEditedQuestion(QuestionType type) {
        IQuestion question = null;
        switch (type) {
            case FILLIN:
                String bodyStr = ((EditText) editLayout.findViewById(R.id.edit_fillin_body)).getText().toString();
                String answerStr = ((EditText) editLayout.findViewById(R.id.edit_fillin_answer)).getText().toString();
                FillInQuestion fillInQuestion = new FillInQuestion.Builder().build();
                fillInQuestion.getBody().setContent(bodyStr);
                fillInQuestion.getAnswer().setContent(answerStr);
                question = fillInQuestion;
                break;
            case TF:

                break;
            case SINGLECHOOSE:
                break;
            case MUTTICHOOSE:
                break;
            case DISCUSS:
                break;
            default:
                break;
        }
        return question;
    }


    public class QuestionPositionAdapter extends BaseAdapter {

        private ArrayList<Boolean> answeredList = new ArrayList();
        private Context context;

        public QuestionPositionAdapter(ArrayList<Boolean> answeredList, Context context) {
            this.answeredList = answeredList;
            this.context = context;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.activity_answer_study_gridview_item, null);
            }
            ((TextView) convertView.findViewById(R.id.question_position)).setText("" + (position + 1));
            if (answeredList.get(position)) {
                ((TextView) convertView.findViewById(R.id.question_position)).
                        setBackground(context.getResources().getDrawable(R.drawable.circle_background_upside_lime));
            }
            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public int getCount() {
            return answeredList.size();
        }
    }
}
