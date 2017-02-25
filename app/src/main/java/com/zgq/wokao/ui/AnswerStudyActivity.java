package com.zgq.wokao.ui;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zgq.wokao.R;
import com.zgq.wokao.Util.DateUtil;
import com.zgq.wokao.ui.adapter.BaseStudySystemAdapter;
import com.zgq.wokao.ui.adapter.DiscussQuestionAdapter;
import com.zgq.wokao.ui.adapter.FillInQuestionAdapter;
import com.zgq.wokao.ui.adapter.MultChoQuestionAdapter;
import com.zgq.wokao.ui.adapter.SglChoQuestionAdapter;
import com.zgq.wokao.ui.adapter.TFQuestionAdapter;
import com.zgq.wokao.model.paper.Constant;
import com.zgq.wokao.model.paper.DiscussQuestion;
import com.zgq.wokao.model.paper.FillInQuestion;
import com.zgq.wokao.model.paper.MultChoQuestion;
import com.zgq.wokao.model.paper.MyQuestionAnswer;
import com.zgq.wokao.model.paper.NormalExamPaper;
import com.zgq.wokao.model.paper.Question;
import com.zgq.wokao.model.paper.QuestionAnswer;
import com.zgq.wokao.model.paper.SglChoQuestion;
import com.zgq.wokao.model.paper.TFQuestion;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class AnswerStudyActivity extends AppCompatActivity implements View.OnClickListener {

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
    LinearLayout questionListTv;

    //题号列表
    @BindView(R.id.question_num_list)
    GridView questionListGv;

    //底部menu
    @BindView(R.id.activity_study_bottom_menu)
    RelativeLayout bottomMenu;

    //弹出底部menu时用于遮挡ViewPager
    @BindView(R.id.background)
    LinearLayout background;

    //根View,//初始时将底部View放在屏幕底部，使用post是因为在onCreat中View没有绘制完全，getHeight返回0
    @BindView(R.id.root_view)
    RelativeLayout rootView;

    private Realm realm = Realm.getDefaultInstance();
    private NormalExamPaper normalExamPaper;

    private int currentQuestionType = Constant.FILLINQUESTIONTYPE;

    private ArrayList<Question> currentAllQuestions = new ArrayList<>();
    private ArrayList<Boolean>  allAnsweredList = new ArrayList<>();
    private PagerAdapter        currentAllQstAdapter;
    private ArrayList<QuestionAnswer> currentAllMyAnswer = new ArrayList<>();

    private ArrayList<Question> currentStarQuestions = new ArrayList<>();
    private ArrayList<Boolean>  starAnsweredList = new ArrayList<>();
    private PagerAdapter        currentStarQstAdapter;
    private ArrayList<QuestionAnswer> currentStarMyAnswer = new ArrayList<>();

    private final int ALLQUESTIONMODE = 1;
    private final int STARQUESTIONMODE = 2;

    private  int currentMode = ALLQUESTIONMODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_study);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void initData() {
        initPaperData();
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
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                currentAllQuestions.get(0).setStudied(true);
                normalExamPaper.getPaperInfo().setLastStudyDate(DateUtil.getCurrentDate());
//                Log.d("----->>last",DateUtil.getCurrentDate());
            }
        });
    }

    private void initPaperData(){
        Intent intent = getIntent();
        String title = intent.getStringExtra("paperTitle");
        String author = intent.getStringExtra("paperAuthor");
        currentQuestionType = intent.getIntExtra("qstType", Constant.FILLINQUESTIONTYPE);
        RealmResults<NormalExamPaper> papers = realm.where(NormalExamPaper.class).
                equalTo("paperInfo.title", title).
                equalTo("paperInfo.author", author).
                findAll();
        normalExamPaper = papers.get(0);
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                int count = normalExamPaper.getPaperInfo().getStudyCount();
//                normalExamPaper.getPaperInfo().setStudyCount(count+1);
//            }
//        });
    }

    //初始化当前的问题
    private void initCurrentQuestionList(){
        currentAllQuestions.clear();
        switch (currentQuestionType){
            case Constant.FILLINQUESTIONTYPE:
                for (FillInQuestion question: normalExamPaper.getFillInQuestions()){
                    currentAllQuestions.add(question);
                }
                break;
            case Constant.TFQUESTIONTYPE:
                for (TFQuestion question: normalExamPaper.getTfQuestions()){
                    currentAllQuestions.add(question);
                }
                break;
            case Constant.SGLCHOQUESTIONTYPE:
                for (SglChoQuestion question: normalExamPaper.getSglChoQuestions()){
                    currentAllQuestions.add(question);
                }
                break;
            case Constant.MULTCHOQUESTIONTYPE:
                for (MultChoQuestion question: normalExamPaper.getMultChoQuestions()){
                    currentAllQuestions.add(question);
                }
                break;
            case Constant.DISCUSSQUESTIONTYPE:
                for (DiscussQuestion question: normalExamPaper.getDiscussQuestions()){
                    currentAllQuestions.add(question);
                }
                break;
        }
    }

    //初始化是否回答过的记录链表
    private void initAnsweredList(){
        allAnsweredList.clear();
        for (int i = 0; i< currentAllQuestions.size(); i++){
            allAnsweredList.add(false);
        }
    }
    //初始化当前的已经加星的问题链表
    private void initCurrentStaredQuestion(){
        currentStarQuestions.clear();
        for (Question question: currentAllQuestions){
            if (question.isStared()) currentStarQuestions.add(question);
        }
    }

    private void initStarAnsweredList(){
        starAnsweredList.clear();
        for (Question question: currentAllQuestions){
            int i = currentAllQuestions.indexOf(question);
            if (question.isStared())
                starAnsweredList.add(allAnsweredList.get(i));
        }
    }
    private void initCurrentMyAllAnswer(){
        currentAllMyAnswer.clear();
        for (int i = 0; i< currentAllQuestions.size(); i++){
            MyQuestionAnswer answer = new MyQuestionAnswer();
            currentAllMyAnswer.add(answer);
        }
    }

    private void initCurrentMyStarAnswer(){
        currentStarMyAnswer.clear();
        for (Question question: currentAllQuestions){
            int i = currentAllQuestions.indexOf(question);
            if (question.isStared())
                currentStarMyAnswer.add(currentAllMyAnswer.get(i));
        }
    }

    private boolean isNeedAnswer(){
        if (currentQuestionType == Constant.TFQUESTIONTYPE || currentQuestionType == Constant.SGLCHOQUESTIONTYPE ||
                currentQuestionType == Constant.MULTCHOQUESTIONTYPE) {
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
        if (currentQuestionType == Constant.TFQUESTIONTYPE || currentQuestionType == Constant.SGLCHOQUESTIONTYPE){
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
                //设置已经学习过了这个问题 //重置该位置的myAnswer
                if (currentMode == ALLQUESTIONMODE){
//                    if (isNeedAnswer()) {
//                        currentAllMyAnswer.get(position).setAnswer("");
//                    }
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            currentAllQuestions.get(p).setStudied(true);
                        }
                    });
                }else{
//                    if (isNeedAnswer()) {
//                        currentStarMyAnswer.get(position).setAnswer("");
//                    }
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            currentStarQuestions.get(p).setStudied(true);
                        }
                    });
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        upDateBottomMenu(getCurrentQstAdapter().getCurrentPosition());
    }

    private void initQstPstGridView(){
        questionListGv.setAdapter(new QuestionPositionAdapter(allAnsweredList,this.getApplicationContext()));
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
    }



    private void upDateBottomMenu(int position){
        switch (getCurrentMode()){
            case ALLQUESTIONMODE:
                if (currentAllQuestions.get(position).isStared()){
                    starLabel.setBackground(getResources().getDrawable(R.drawable.active_star));
                }else{
                    starLabel.setBackground(getResources().getDrawable(R.drawable.inactive_star));
                }
                if (allAnsweredList.get(position)){
                    answerLabel.setBackground(getResources().getDrawable(R.drawable.active_light));
                }else {
                    answerLabel.setBackground(getResources().getDrawable(R.drawable.inactive_light));
                }
                break;
            case STARQUESTIONMODE:
                if (currentStarQuestions.size() == 0) return;
                if (currentStarQuestions.get(position).isStared()){
                    starLabel.setBackground(getResources().getDrawable(R.drawable.active_star));
                }else{
                    starLabel.setBackground(getResources().getDrawable(R.drawable.inactive_star));
                }
                if (starAnsweredList.get(position)){
                    answerLabel.setBackground(getResources().getDrawable(R.drawable.active_light));
                }else {
                    answerLabel.setBackground(getResources().getDrawable(R.drawable.inactive_light));
                }
                break;
        }

    }

    public int getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(int mode){
        currentMode = mode;
    }

    private BaseStudySystemAdapter getCurrentQstAdapter(){
        return ((BaseStudySystemAdapter) viewPager.getAdapter());
    }

    private void setStar(){
        if (currentMode == STARQUESTIONMODE && currentStarQuestions.size() == 0) return;
        final int position = getCurrentQstAdapter().getCurrentPosition();
        switch(getCurrentMode()){
            case ALLQUESTIONMODE:
                if (currentAllQuestions.get(position).isStared()){
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            currentAllQuestions.get(position).setStared(false);
                        }
                    });
                    starLabel.setBackground(getResources().getDrawable(R.drawable.inactive_star));
                }else{
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            currentAllQuestions.get(position).setStared(true);
                        }
                    });
                    starLabel.setBackground(getResources().getDrawable(R.drawable.active_star));
                }
                break;
            case STARQUESTIONMODE:
                if (currentStarQuestions.get(position).isStared()){
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            currentStarQuestions.get(position).setStared(false);
                        }
                    });
                    starLabel.setBackground(getResources().getDrawable(R.drawable.inactive_star));
                }else{
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            currentStarQuestions.get(position).setStared(true);
                        }
                    });
                    starLabel.setBackground(getResources().getDrawable(R.drawable.active_star));
                }
                break;
        }
    }

    private void showCurrentAnswer(){
        if (currentMode == STARQUESTIONMODE && currentStarQuestions.size() == 0) return;
        switch (getCurrentMode()){
            case ALLQUESTIONMODE:
                getCurrentQstAdapter().showCurrentAnswer();
                allAnsweredList.set(getCurrentQstAdapter().getCurrentPosition(),true);
                answerLabel.setBackground(getResources().getDrawable(R.drawable.active_light));
                break;
            case STARQUESTIONMODE:
                getCurrentQstAdapter().showCurrentAnswer();
                starAnsweredList.set(getCurrentQstAdapter().getCurrentPosition(),true);
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

    private PagerAdapter initCurrentAllQstAdapter() {
        switch (currentQuestionType) {
            case Constant.FILLINQUESTIONTYPE:
                currentAllQstAdapter = new FillInQuestionAdapter(currentAllQuestions, allAnsweredList,this);
                return currentAllQstAdapter;
            case Constant.DISCUSSQUESTIONTYPE:
                currentAllQstAdapter = new DiscussQuestionAdapter(currentAllQuestions, allAnsweredList,this);
                return currentAllQstAdapter;
            case Constant.TFQUESTIONTYPE:
                currentAllQstAdapter = new TFQuestionAdapter(currentAllQuestions,allAnsweredList,currentAllMyAnswer,this);
                return currentAllQstAdapter;
            case Constant.SGLCHOQUESTIONTYPE:
                currentAllQstAdapter = new SglChoQuestionAdapter(currentAllQuestions,allAnsweredList,currentAllMyAnswer,this);
                return currentAllQstAdapter;
            case Constant.MULTCHOQUESTIONTYPE:
                currentAllQstAdapter = new MultChoQuestionAdapter(currentAllQuestions,allAnsweredList,currentAllMyAnswer,this);
                return currentAllQstAdapter;
        }
        return null;
    }

    private PagerAdapter initCurrentStarQstAdapter(){
        switch (currentQuestionType) {
            case Constant.FILLINQUESTIONTYPE:
                currentStarQstAdapter = new FillInQuestionAdapter(currentStarQuestions, starAnsweredList,this);
                return currentStarQstAdapter;
            case Constant.DISCUSSQUESTIONTYPE:
                currentStarQstAdapter = new DiscussQuestionAdapter(currentStarQuestions, starAnsweredList,this);
                return currentStarQstAdapter;
            case Constant.TFQUESTIONTYPE:
                currentStarQstAdapter = new TFQuestionAdapter(currentStarQuestions,starAnsweredList,currentStarMyAnswer,this);
                return currentStarQstAdapter;
            case Constant.SGLCHOQUESTIONTYPE:
                currentStarQstAdapter = new SglChoQuestionAdapter(currentStarQuestions,starAnsweredList,currentStarMyAnswer,this);
                return currentStarQstAdapter;
            case Constant.MULTCHOQUESTIONTYPE:
                currentStarQstAdapter = new MultChoQuestionAdapter(currentStarQuestions,starAnsweredList,currentStarMyAnswer,this);
                return currentStarQstAdapter;
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

                showCurrentAnswer();
                break;
            case R.id.set_star:
                setStar();
                break;
            case R.id.question_list:
                ((QuestionPositionAdapter)(questionListGv.getAdapter())).notifyDataSetChanged();
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
                if (isNeedAnswer())  initCurrentMyStarAnswer();
                initCurrentStarQstAdapter();

                setCurrentMode(STARQUESTIONMODE);

                viewPager.setAdapter(currentStarQstAdapter);
                currentStarQstAdapter.notifyDataSetChanged();
                upDateBottomMenu(getCurrentQstAdapter().getCurrentPosition());
                break;
        }
    }

    public class QuestionPositionAdapter extends BaseAdapter{

        private ArrayList<Boolean> answeredList = new ArrayList();
        private Context context;

        public QuestionPositionAdapter(ArrayList<Boolean> answeredList, Context context) {
            this.answeredList = answeredList;
            this.context =context;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.activity_answer_study_gridview_item,null);
            }
            ((TextView)convertView.findViewById(R.id.question_position)).setText(""+(position+1));
            if (answeredList.get(position)){
                ((TextView)convertView.findViewById(R.id.question_position)).
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
