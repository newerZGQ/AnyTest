package com.zgq.wokao.module.study;

import android.animation.ObjectAnimator;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.adapter.BaseViewPagerAdapter;
import com.zgq.wokao.adapter.DiscussQuestionAdapter;
import com.zgq.wokao.adapter.FillInQuestionAdapter;
import com.zgq.wokao.adapter.MultChoQuestionAdapter;
import com.zgq.wokao.adapter.QuestionIndexAdapter;
import com.zgq.wokao.adapter.SglChoQuestionAdapter;
import com.zgq.wokao.adapter.TFQuestionAdapter;
import com.zgq.wokao.entity.paper.question.IQuestion;
import com.zgq.wokao.injector.components.DaggerStudyComponent;
import com.zgq.wokao.injector.modules.StudyModule;
import com.zgq.wokao.module.base.BaseFragment;
import com.zgq.wokao.module.study.entity.StudyParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class LearningFragment extends BaseFragment<StudyContract.LearningPresenter>
        implements StudyContract.LearningView, View.OnClickListener {

    private static final String TAG = LearningFragment.class.getSimpleName();

    //返回
    @BindView(R.id.toolbar_back)
    TextView toolbarBack;

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
    LinearLayout indexSwitcher;

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

    @Inject
    StudyParams studyParams;

    private BaseViewPagerAdapter questionAdapter;
    private Mode mode = Mode.ALL;
    private IndexState indexState = IndexState.HIDE;

    private QuestionIndexAdapter indexAdapter;

    @Override
    protected void daggerInject() {
        DaggerStudyComponent.builder()
                .applicationComponent(getAppComponent())
                .studyModule(new StudyModule())
                .build()
                .inject(this);
        presenter.initParams(studyParams.getPaperId(),studyParams.getQuestionType(), studyParams.getQuestionId());
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_learning;
    }

    @Override
    protected void initViews() {
        initToolbar();
        initQuestionPager();
        initQuestionIndex();
        bottomMenu.post(() -> ObjectAnimator.ofFloat(bottomMenu, "translationY",
                bottomMenu.getHeight() - setStared.getHeight())
                .setDuration(0)
                .start());
    }

    @Override
    public void showQuestions(List<IQuestion> questions) {
        questionAdapter.replaceData(questions);
    }

    public void showQuestionIndex(List<IQuestion> questions, HashMap<IQuestion, Boolean> answered){
        indexAdapter.replaceData(questions, answered);
    }

    @Override
    public void notifyQuestionIndexChanged() {
        indexAdapter.notifyDataSetChanged();
    }

    @Override
    public void setStarState(boolean isStared) {
        if (isStared){
            starLabel.setBackground(getResources().getDrawable(R.drawable.active_star));
        }else {
            starLabel.setBackground(getResources().getDrawable(R.drawable.inactive_star));
        }
    }

    @Override
    public void setAnswerState(boolean answered) {
        if (answered){
            answerLabel.setBackground(getResources().getDrawable(R.drawable.active_light));
        }else {
            answerLabel.setBackground(getResources().getDrawable(R.drawable.inactive_light));
        }
    }

    @Override
    public void hideAnswerBtn() {
        showAnswerButton.setVisibility(View.GONE);
    }

    @Override
    public void showTargetQuestion(int position) {
        viewPager.setCurrentItem(position);
    }

    private void initToolbar() {
        switchToolbar();
        studyMode.setOnClickListener(this);
        remebMode.setOnClickListener(this);
        toolbarBack.setOnClickListener(this);
        indexSwitcher.setOnClickListener(this);
        showAnswerButton.setOnClickListener(this);
        setStared.setOnClickListener(this);
    }

    private void switchToolbar() {
        switch (mode) {
            case ALL:
                studyMode.setBackground(getResources().getDrawable(R.drawable.toolbar_left_white_halfrect_circle));
                studyMode.setTextColor(getResources().getColor(R.color.colorTeal));
                remebMode.setBackground(getResources().getDrawable(R.drawable.toolbar_right_teal_halfrect_circle));
                remebMode.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
            case STAR:
                remebMode.setBackground(getResources().getDrawable(R.drawable.toolbar_right_white_halfrect_circle));
                remebMode.setTextColor(getResources().getColor(R.color.colorTeal));
                studyMode.setBackground(getResources().getDrawable(R.drawable.toolbar_left_teal_halfrect_circle));
                studyMode.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
            default:
                break;
        }
    }

    private BaseViewPagerAdapter.OnStudiedListener studiedListener = new BaseViewPagerAdapter.OnStudiedListener() {
        @Override
        public void onStudied(IQuestion question, boolean correct) {
            presenter.updateStudiedRecord(question, correct);
        }

        @Override
        public void starQuestion(IQuestion question) {
            presenter.starQuestion(question);
        }

        @Override
        public void onSelected(IQuestion question, int position) {
            presenter.onLoadQuestion(question);
        }
    };

    private void initQuestionPager() {
        switch (studyParams.getQuestionType()) {
            case FILLIN:
                questionAdapter = new FillInQuestionAdapter(new ArrayList<>(),studiedListener);
                break;
            case TF:
                questionAdapter = new TFQuestionAdapter(new ArrayList<>(),studiedListener);
                break;
            case SINGLECHOOSE:
                questionAdapter = new SglChoQuestionAdapter(new ArrayList<>(),studiedListener);
                break;
            case MUTTICHOOSE:
                questionAdapter = new MultChoQuestionAdapter(new ArrayList<>(),studiedListener);
                break;
            case DISCUSS:
                questionAdapter = new DiscussQuestionAdapter(new ArrayList<>(),studiedListener);
                break;
            default:
                break;
        }
        viewPager.setAdapter(questionAdapter);
    }

    private AdapterView.OnItemClickListener onClickIndexListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            viewPager.setCurrentItem(i);
            switchIndexView();
        }
    };

    private void initQuestionIndex(){
        indexAdapter = new QuestionIndexAdapter(new ArrayList<>(0),
                new HashMap<>(0));
        questionListGv.setAdapter(indexAdapter);
        questionListGv.setOnItemClickListener(onClickIndexListener);
    }

    private void switchIndexView(){
        if (indexState == IndexState.HIDE){
            background.setVisibility(View.VISIBLE);
            ObjectAnimator.ofFloat(bottomMenu, "translationY", 0)
                    .setDuration(300)
                    .start();
            indexState = IndexState.SHOW;
        }else{
            background.setVisibility(View.GONE);
            ObjectAnimator.ofFloat(bottomMenu, "translationY",
                    bottomMenu.getHeight() - setStared.getHeight())
                    .setDuration(300)
                    .start();
            indexState = IndexState.HIDE;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                getActivity().finish();
                break;
            case R.id.all_question:
                if (mode == Mode.STAR) {
                    presenter.loadAllQuestions();
                    mode = Mode.ALL;
                    switchToolbar();
                }
                break;
            case R.id.stared_question:
                if (mode == Mode.ALL) {
                    presenter.loadStarQuestions();
                    mode = Mode.STAR;
                    switchToolbar();
                }
                break;
            case R.id.question_list:
                switchIndexView();
                break;
            case R.id.show_answer:
                questionAdapter.showCurrentAnswer();
                break;
            case R.id.set_star:
                questionAdapter.starCurrentQuestion();
                break;
            default:
                break;
        }
    }

    private enum Mode {
        ALL, STAR
    }

    private enum IndexState{
        HIDE,SHOW
    }
}
