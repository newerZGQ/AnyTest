package com.zgq.wokao.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.umeng.analytics.MobclickAgent;
import com.zgq.wokao.R;
import com.zgq.wokao.Util.DateUtil;
import com.zgq.wokao.model.paper.Constant;
import com.zgq.wokao.model.paper.DiscussQuestion;
import com.zgq.wokao.model.paper.ExamPaperInfo;
import com.zgq.wokao.model.paper.FillInQuestion;
import com.zgq.wokao.model.paper.MultChoQuestion;
import com.zgq.wokao.model.paper.NormalExamPaper;
import com.zgq.wokao.model.paper.SglChoQuestion;
import com.zgq.wokao.model.paper.TFQuestion;
import com.zgq.wokao.view.ObservableScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;;
import io.realm.RealmList;
import io.realm.RealmResults;

public class QuestionsListActivity extends AppCompatActivity implements View.OnClickListener {

    private ObservableScrollView obsscrollView;

    private ScrollView rootView;

    private ImageView headZoomView;
    private LinearLayout setStarLayout;
    private TextView setStarText;
    private TextView setStarLabel;
    @BindView(R.id.toolbar_back)
    TextView toolbarBack;
    @BindView(R.id.toolbar_layout)
    LinearLayout toolbarLayout;

    private RelativeLayout fiQstTypeInfo;
    private RelativeLayout tfQstTypeInfo;
    private RelativeLayout scQstTypeInfo;
    private RelativeLayout mcQstTypeInfo;
    private RelativeLayout dsQstTypeInfo;

    private Realm realm = Realm.getDefaultInstance();

    private String paperTitle;
    private String paperAuthor;
    private int paperQuestionCount;
    private int paperStudyCount;
    private String lastStudyDate;
    private boolean isPaperStar;

    private int[] background = new int[3];

    private NormalExamPaper normalExamPaper;
    private ExamPaperInfo info;

    private int toolbarLayoutHeight;
    private int zoomViewHeight;
    private int slidingDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(R.layout.activity_questions_list);
        ButterKnife.bind(this);
        initToolbar();
        loadViewForCode();
        initView();
        toolbarLayout.post(new Runnable() {
            @Override
            public void run() {
                toolbarLayoutHeight = toolbarLayout.getHeight();
            }
        });
        headZoomView.post(new Runnable() {
            @Override
            public void run() {
                zoomViewHeight = headZoomView.getHeight();
                slidingDistance = zoomViewHeight - toolbarLayoutHeight;
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        slidingDistance = headZoomView.getHeight() - toolbarLayout.getHeight();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    private void initToolbar() {
        toolbarBack.setOnClickListener(this);
    }

    private void initData() {
        background[0] = R.drawable.image_concept;
        background[1] = R.drawable.image_grassland;
        background[2] = R.drawable.image_night_star;

        String title = getIntent().getStringExtra("paperTitle");
        String author = getIntent().getStringExtra("paperAuthor");
        RealmResults<NormalExamPaper> papers = realm.where(NormalExamPaper.class).
                equalTo("paperInfo.title", title).
                equalTo("paperInfo.author", author).
                findAll();

        normalExamPaper = papers.get(0);
        info = normalExamPaper.getPaperInfo();
        paperTitle = info.getTitle();
        paperAuthor = info.getAuthor();
        if (info.getLastStudyDate() == null || info.getLastStudyDate().equals("")){
            lastStudyDate = "未学习";
        }else {
            lastStudyDate = DateUtil.formatDisplayTime(info.getLastStudyDate(), null);
        }
//        Log.d("------>>laststudy",lastStudyDate);
        isPaperStar = info.isStared();
        paperStudyCount = getStudiedCount(normalExamPaper);
//        Log.d("---->>", "" + paperStudyCount);
    }

    private void initView() {
        headZoomView = (ImageView) obsscrollView.getPullRootView().findViewById(R.id.iv_zoom);
        Glide.with(this).load(background[(int)(Math.random()*3)]).into(headZoomView);
        RealmList<FillInQuestion> fiQstList = normalExamPaper.getFillInQuestions();
        RealmList<TFQuestion> tfQstList = normalExamPaper.getTfQuestions();
        RealmList<SglChoQuestion> scQstList = normalExamPaper.getSglChoQuestions();
        RealmList<MultChoQuestion> mcQstList = normalExamPaper.getMultChoQuestions();
        RealmList<DiscussQuestion> dsQstList = normalExamPaper.getDiscussQuestions();

        ((TextView) obsscrollView.getPullRootView().findViewById(R.id.paper_title)).setText(paperTitle);
        ((TextView) obsscrollView.getPullRootView().findViewById(R.id.paper_author)).setText(paperAuthor);
        ((TextView) obsscrollView.getPullRootView().findViewById(R.id.study_count)).setText("" + paperStudyCount);
        ((TextView) obsscrollView.getPullRootView().findViewById(R.id.laststudy_date)).setText(lastStudyDate);

        setStarLayout = (LinearLayout) obsscrollView.getPullRootView().findViewById(R.id.set_star_layout);
        setStarText = (TextView) obsscrollView.getPullRootView().findViewById(R.id.set_star_text);
        setStarLabel = (TextView) obsscrollView.getPullRootView().findViewById(R.id.set_star_label);
        if (info.isStared()) {
            setStarLabel.setBackground(getResources().getDrawable(R.drawable.active_star));
            setStarText.setText("已收藏");
        } else {
            setStarLabel.setBackground(getResources().getDrawable(R.drawable.inactive_star));
            setStarText.setText("未收藏");
        }
        setStarLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info.isStared()) {
                    setStarLabel.setBackground(getResources().getDrawable(R.drawable.inactive_star));
                    setStarText.setText("未收藏");
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            info.setStared(false);
                        }
                    });
                } else {
                    setStarLabel.setBackground(getResources().getDrawable(R.drawable.active_star));
                    setStarText.setText("已收藏");
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            info.setStared(true);
                        }
                    });
                }
            }
        });


        paperQuestionCount = fiQstList.size() + tfQstList.size() + scQstList.size() + mcQstList.size() + dsQstList.size();
        ((TextView) obsscrollView.getPullRootView().findViewById(R.id.paper_question_count)).setText("" + paperQuestionCount);

        if (fiQstList.size() != 0) {
            fiQstTypeInfo = (RelativeLayout) obsscrollView.getPullRootView().findViewById(R.id.fiqstType);
            fiQstTypeInfo.setVisibility(View.VISIBLE);
            int count = 0;
            for (FillInQuestion fillInQuestion : fiQstList) {
                if (fillInQuestion.isStared()) count++;
            }
            setQuestionTypeInfo(fiQstTypeInfo, R.drawable.circle_background_upside_cyan, "填", "填空题", "共" + fiQstList.size() + "题, " + "收藏" + count + "题");
        } else {
            fiQstTypeInfo = (RelativeLayout) obsscrollView.getPullRootView().findViewById(R.id.fiqstType);
            fiQstTypeInfo.setVisibility(View.GONE);
        }

        if (tfQstList.size() != 0) {
            tfQstTypeInfo = (RelativeLayout) obsscrollView.getPullRootView().findViewById(R.id.tfqstType);
            tfQstTypeInfo.setVisibility(View.VISIBLE);
            int count = 0;
            for (TFQuestion tfQuestion : tfQstList) {
                if (tfQuestion.isStared()) count++;
            }
            setQuestionTypeInfo(tfQstTypeInfo, R.drawable.circle_background_upside_orange, "判", "判断题", "共" + tfQstList.size() + "题, " + "收藏" + count + "题");
        } else {
            tfQstTypeInfo = (RelativeLayout) obsscrollView.getPullRootView().findViewById(R.id.tfqstType);
            tfQstTypeInfo.setVisibility(View.GONE);
        }

        if (scQstList.size() != 0) {
            scQstTypeInfo = (RelativeLayout) obsscrollView.getPullRootView().findViewById(R.id.scqstType);
            scQstTypeInfo.setVisibility(View.VISIBLE);
            int count = 0;
            for (SglChoQuestion sglChoQuestion : scQstList) {
                if (sglChoQuestion.isStared()) count++;
            }
            setQuestionTypeInfo(scQstTypeInfo, R.drawable.circle_background_upside_lime, "单", "单项选择题", "共" + scQstList.size() + "题, " + "收藏" + count + "题");
        } else {
            scQstTypeInfo = (RelativeLayout) obsscrollView.getPullRootView().findViewById(R.id.scqstType);
            scQstTypeInfo.setVisibility(View.GONE);
        }

        if (mcQstList.size() != 0) {
            mcQstTypeInfo = (RelativeLayout) obsscrollView.getPullRootView().findViewById(R.id.mcqstType);
            mcQstTypeInfo.setVisibility(View.VISIBLE);
            int count = 0;
            for (MultChoQuestion multChoQuestion : mcQstList) {
                if (multChoQuestion.isStared()) count++;
            }
            setQuestionTypeInfo(mcQstTypeInfo, R.drawable.circle_background_upside_teal, "多", "多项选择题", "共" + mcQstList.size() + "题, " + "收藏" + count + "题");
        } else {
            mcQstTypeInfo = (RelativeLayout) obsscrollView.getPullRootView().findViewById(R.id.mcqstType);
            mcQstTypeInfo.setVisibility(View.GONE);
        }

        if (dsQstList.size() != 0) {
            dsQstTypeInfo = (RelativeLayout) obsscrollView.getPullRootView().findViewById(R.id.dsqstType);
            dsQstTypeInfo.setVisibility(View.VISIBLE);
            int count = 0;
            for (DiscussQuestion discussQuestion : dsQstList) {
                if (discussQuestion.isStared()) count++;
            }
            setQuestionTypeInfo(dsQstTypeInfo, R.drawable.circle_background_upside_green, "简", "简答题", "共" + dsQstList.size() + "题, " + "收藏" + count + "题");
        } else {
            dsQstTypeInfo = (RelativeLayout) obsscrollView.getPullRootView().findViewById(R.id.dsqstType);
            dsQstTypeInfo.setVisibility(View.GONE);
        }
    }

    private void startSduty(String paperTitle, String paperAuthor, int qstType) {
        Intent intent = new Intent();
        intent.putExtra("paperTitle", paperTitle);
        intent.putExtra("paperAuthor", paperAuthor);
        intent.putExtra("qstType", qstType);
        intent.setClass(this, AnswerStudyActivity.class);
        startActivity(intent);
    }

    private void setQuestionTypeInfo(RelativeLayout layout, int label, String labelText, String qstType, String qstInfo) {
        ((TextView) layout.findViewById(R.id.question_label)).setBackground(getResources().getDrawable(label));
        ((TextView) layout.findViewById(R.id.question_label)).setText(labelText);
        ((TextView) layout.findViewById(R.id.question_type)).setText(qstType);
        ((TextView) layout.findViewById(R.id.questiontype_info)).setText(qstInfo);
        layout.setOnClickListener(this);
    }

    private void loadViewForCode() {
        obsscrollView = (ObservableScrollView) findViewById(R.id.scroll_view);
        View headView = LayoutInflater.from(this).inflate(R.layout.activity_questionlist_head_view, null, false);
        View zoomView = LayoutInflater.from(this).inflate(R.layout.activity_questionlist_zoom_view, null, false);
        View contentView = LayoutInflater.from(this).inflate(R.layout.activity_questionlist_content_view, null, false);
        obsscrollView.setHeaderView(headView);
        obsscrollView.setZoomView(zoomView);
        obsscrollView.setScrollContentView(contentView);

        obsscrollView.setParallax(false);
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, mScreenWidth);
        obsscrollView.setHeaderLayoutParams(localObject);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.fiqstType:
                startSduty(paperTitle, paperAuthor, Constant.FILLINQUESTIONTYPE);
                break;
            case R.id.tfqstType:
                startSduty(paperTitle, paperAuthor, Constant.TFQUESTIONTYPE);
                break;
            case R.id.scqstType:
                startSduty(paperTitle, paperAuthor, Constant.SGLCHOQUESTIONTYPE);
                break;
            case R.id.mcqstType:
                startSduty(paperTitle, paperAuthor, Constant.MULTCHOQUESTIONTYPE);
                break;
            case R.id.dsqstType:
                startSduty(paperTitle, paperAuthor, Constant.DISCUSSQUESTIONTYPE);
                break;
        }
    }

    public int getStudiedCount(final NormalExamPaper normalExamPaper) {
        RealmList<FillInQuestion> fillInQuestions = normalExamPaper.getFillInQuestions();
        RealmList<TFQuestion> tfQuestions = normalExamPaper.getTfQuestions();
        RealmList<SglChoQuestion> sglChoQuestions = normalExamPaper.getSglChoQuestions();
        RealmList<MultChoQuestion> multChoQuestions = normalExamPaper.getMultChoQuestions();
        RealmList<DiscussQuestion> discussQuestions = normalExamPaper.getDiscussQuestions();
        for (int i = 0; i < fillInQuestions.size(); i++) {
//            Log.d("----->>", "fi" +i+ fillInQuestions.get(i).isStudied());
            if (!fillInQuestions.get(i).isStudied())
                return normalExamPaper.getPaperInfo().getStudyCount();
        }
        for (int i = 0; i < tfQuestions.size(); i++) {
//            Log.d("----->>", "tf" +i+ tfQuestions.get(i).isStudied());
            if (!tfQuestions.get(i).isStudied())
                return normalExamPaper.getPaperInfo().getStudyCount();
        }
        for (int i = 0; i < sglChoQuestions.size(); i++) {
//            Log.d("----->>", "sg" +i+ sglChoQuestions.get(i).isStudied());
            if (!sglChoQuestions.get(i).isStudied())
                return normalExamPaper.getPaperInfo().getStudyCount();
        }
        for (int i = 0; i < multChoQuestions.size(); i++) {
//            Log.d("----->>", "mu" +i+ multChoQuestions.get(i).isStudied());
            if (!multChoQuestions.get(i).isStudied())
                return normalExamPaper.getPaperInfo().getStudyCount();
        }
        for (int i = 0; i < discussQuestions.size(); i++) {
//            Log.d("----->>", "di" +i+ discussQuestions.get(i).isStudied());
            if (!discussQuestions.get(i).isStudied())
                return normalExamPaper.getPaperInfo().getStudyCount();
        }
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                int count = normalExamPaper.getPaperInfo().getStudyCount();
                normalExamPaper.getPaperInfo().setStudyCount(count + 1);
            }
        });
        setAllQuestionUnstudied(normalExamPaper);
        return normalExamPaper.getPaperInfo().getStudyCount();
    }

    private void setAllQuestionUnstudied(NormalExamPaper normalExamPaper) {
        final RealmList<FillInQuestion> fillInQuestions = normalExamPaper.getFillInQuestions();
        final RealmList<TFQuestion> tfQuestions = normalExamPaper.getTfQuestions();
        final RealmList<SglChoQuestion> sglChoQuestions = normalExamPaper.getSglChoQuestions();
        final RealmList<MultChoQuestion> multChoQuestions = normalExamPaper.getMultChoQuestions();
        final RealmList<DiscussQuestion> discussQuestions = normalExamPaper.getDiscussQuestions();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (int i = 0; i < fillInQuestions.size(); i++) {
                    fillInQuestions.get(i).setStudied(false);
                }
                for (int i = 0; i < tfQuestions.size(); i++) {
                    tfQuestions.get(i).setStudied(false);
                }
                for (int i = 0; i < sglChoQuestions.size(); i++) {
                    sglChoQuestions.get(i).setStudied(false);
                }
                for (int i = 0; i < multChoQuestions.size(); i++) {
                    multChoQuestions.get(i).setStudied(false);
                }
                for (int i = 0; i < discussQuestions.size(); i++) {
                    discussQuestions.get(i).setStudied(false);
                }
            }
        });
//        for (int i = 0; i < tfQuestions.size(); i++) {
//            tfQuestions.get(i).setStudied(false);
//        }
//        for (int i = 0; i < sglChoQuestions.size(); i++) {
//            sglChoQuestions.get(i).setStudied(false);
//        }
//        for (int i = 0; i < multChoQuestions.size(); i++) {
//            multChoQuestions.get(i).setStudied(false);
//        }
//        for (int i = 0; i < discussQuestions.size(); i++) {
//            discussQuestions.get(i).setStudied(false);
//        }
    }
}
