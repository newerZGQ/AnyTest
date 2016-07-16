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

import com.zgq.wokao.R;
import com.zgq.wokao.adapter.BaseStudySystemAdapter;
import com.zgq.wokao.adapter.FillInQuestionAdapter;
import com.zgq.wokao.data.Constant;
import com.zgq.wokao.data.DiscussQuestion;
import com.zgq.wokao.data.FillInQuestion;
import com.zgq.wokao.data.MultChoQuestion;
import com.zgq.wokao.data.NormalExamPaper;
import com.zgq.wokao.data.Question;
import com.zgq.wokao.data.SglChoQuestion;
import com.zgq.wokao.data.TFQuestion;


import java.util.ArrayList;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class AnswerStudyActivity extends AppCompatActivity implements View.OnClickListener {

    //返回
    @BindView(R.id.toolbar_back)
    TextView toolbarBack;

    //切换模式
    @BindView(R.id.study_mode)
    TextView studyMode;
    @BindView(R.id.remeb_mode)
    TextView remebMode;

    //ViewPager
    @BindView(R.id.answer_study_pager)
    ViewPager viewPager;
    private PagerAdapter adapter;

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
    private ArrayList<Question> currentQuestions = new ArrayList<>();
    private ArrayList<Boolean>  answeredList = new ArrayList<>();

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
        String title = intent.getStringExtra("paperTitle");
        String author = intent.getStringExtra("paperAuthor");
        currentQuestionType = intent.getIntExtra("qstType", Constant.FILLINQUESTIONTYPE);
        RealmResults<NormalExamPaper> papers = realm.where(NormalExamPaper.class).
                equalTo("paperInfo.title", title).
                equalTo("paperInfo.author", author).
                findAll();
        normalExamPaper = papers.get(0);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                int count = normalExamPaper.getPaperInfo().getStudyCount();
                normalExamPaper.getPaperInfo().setStudyCount(count+1);
//                normalExamPaper.getPaperInfo().setLastStudyDate();
            }
        });
        initQuestionList();
    }

    private void initView() {
        initToolbar();
        viewPager.setAdapter(initCurrentAdapter());
        background.setVisibility(View.GONE);
        background.setOnClickListener(this);
        showAnswerButton.setOnClickListener(this);
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
                upDateBottomMenu(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initQstPstGridView(){
        questionListGv.setAdapter(new QuestionPositionAdapter(answeredList,this.getApplicationContext()));
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

    private void initQuestionList(){
        currentQuestions.clear();
        answeredList.clear();
        switch (currentQuestionType){
            case Constant.FILLINQUESTIONTYPE:
                for (FillInQuestion question: normalExamPaper.getFillInQuestions()){
                    currentQuestions.add(question);
                    answeredList.add(false);
                }
                break;
            case Constant.TFQUESTIONTYPE:
                for (TFQuestion question: normalExamPaper.getTfQuestions()){
                    currentQuestions.add(question);
                    answeredList.add(false);
                }
                break;
            case Constant.SGLCHOQUESTIONTYPE:
                for (SglChoQuestion question: normalExamPaper.getSglChoQuestions()){
                    currentQuestions.add(question);
                    answeredList.add(false);
                }
                break;
            case Constant.MULTCHOQUESTIONTYPE:
                for (MultChoQuestion question: normalExamPaper.getMultChoQuestions()){
                    currentQuestions.add(question);
                    answeredList.add(false);
                }
                break;
            case Constant.DISCUSSQUESTIONTYPE:
                for (DiscussQuestion question: normalExamPaper.getDiscussQuestions()){
                    currentQuestions.add(question);
                    answeredList.add(false);
                }
                break;
        }
    }

    private void upDateBottomMenu(int position){
        if (currentQuestions.get(position).isStared()){
            starLabel.setBackground(getResources().getDrawable(R.drawable.active_star));
        }else{
            starLabel.setBackground(getResources().getDrawable(R.drawable.inactive_star));
        }
        if (answeredList.get(position)){
            answerLabel.setBackground(getResources().getDrawable(R.drawable.active_light));
        }else {
            answerLabel.setBackground(getResources().getDrawable(R.drawable.inactive_light));
        }
    }

    private BaseStudySystemAdapter getAdapter(){
        return ((BaseStudySystemAdapter) viewPager.getAdapter());
    }

    private void setStar(){
        final int position = getAdapter().getCurrentPosition();
        if (currentQuestions.get(position).isStared()){
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    currentQuestions.get(position).setStared(false);
                }
            });
            starLabel.setBackground(getResources().getDrawable(R.drawable.inactive_star));
        }else{
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    currentQuestions.get(position).setStared(true);
                }
            });
            starLabel.setBackground(getResources().getDrawable(R.drawable.active_star));
        }
    }

    private void showCurrentAnswer(){
        getAdapter().showCurrentAnswer();
        answeredList.set(getAdapter().getCurrentPosition(),true);
        answerLabel.setBackground(getResources().getDrawable(R.drawable.active_light));
    }

    private void showQuestionList() {
        background.setVisibility(View.VISIBLE);
        ObjectAnimator.ofFloat(bottomMenu, "translationY", 0).setDuration(300).start();
    }

    private void hideQuestionList() {
        background.setVisibility(View.GONE);
        ObjectAnimator.ofFloat(bottomMenu, "translationY", bottomMenu.getHeight() - setStared.getHeight()).setDuration(300).start();
    }

    private PagerAdapter initCurrentAdapter() {
        switch (currentQuestionType) {
            case Constant.FILLINQUESTIONTYPE:
                return new FillInQuestionAdapter(currentQuestions, answeredList,this);
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
                showQuestionList();
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.study_mode:
                studyMode.setBackground(getResources().getDrawable(R.drawable.activity_asstd_left_white_halfrect_circle));
                studyMode.setTextColor(getResources().getColor(R.color.colorTeal));
                remebMode.setBackground(getResources().getDrawable(R.drawable.activity_asstd_right_teal_halfrect_circle));
                remebMode.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
            case R.id.remeb_mode:
                remebMode.setBackground(getResources().getDrawable(R.drawable.activity_asstd_right_white_halfrect_circle));
                remebMode.setTextColor(getResources().getColor(R.color.colorTeal));
                studyMode.setBackground(getResources().getDrawable(R.drawable.activity_asstd_left_teal_halfrect_circle));
                studyMode.setTextColor(getResources().getColor(R.color.colorWhite));
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
            ((TextView)convertView).setText(""+(position+1));
            if (answeredList.get(position)){
                ((TextView)convertView).setBackground(context.getResources().getDrawable(R.drawable.circle_background_upside_blue));
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
