package com.zgq.wokao.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zgq.wokao.R;
import com.zgq.wokao.data.NormalExamPaper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;;
import io.realm.RealmResults;

public class QuestionsListActivity extends AppCompatActivity {
    private PullToZoomScrollViewEx scrollView;

    private ImageView headZoomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_list);
        loadViewForCode();
        scrollView = (PullToZoomScrollViewEx) findViewById(R.id.scroll_view);
        scrollView.setParallax(false);
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth,mScreenWidth);
        scrollView.setHeaderLayoutParams(localObject);
        headZoomView = (ImageView) scrollView.getPullRootView().findViewById(R.id.iv_zoom);
        Glide.with(this).load(R.drawable.back).into(headZoomView);
    }

    private void loadViewForCode() {
        PullToZoomScrollViewEx scrollView = (PullToZoomScrollViewEx) findViewById(R.id.scroll_view);
        View headView = LayoutInflater.from(this).inflate(R.layout.activity_questionlist_head_view, null, false);
        View zoomView = LayoutInflater.from(this).inflate(R.layout.activity_questionlist_zoom_view, null, false);
        View contentView = LayoutInflater.from(this).inflate(R.layout.activity_questionlist_content_view, null, false);
        scrollView.setHeaderView(headView);
        scrollView.setZoomView(zoomView);
        scrollView.setScrollContentView(contentView);
    }

//    private static final int FILLINQUESTIONLABEL = 1;
//    private static final int TFQUESTIONLABEL = 2;
//    private static final int SGLCHOQUESTIONLABEL = 3;
//    private static final int MULTCHOQUESTIONLABEL = 4;
//    private static final int DISCUSSQUESTIONLABEL = 5;
//
////    @BindView(R.id.main_vp_container)
////    RecyclerView questionTypeList;
////    QuestionTypeAdapter adapter;
//
//    private ArrayList<String> typeNames = new ArrayList<>();
//    private ArrayList<Integer> typeImages = new ArrayList<>();
//    private ArrayList<Integer> questionCount = new ArrayList<>();
//
//    private NormalExamPaper normalExamPaper;
//
//
//
//    private Realm realm = Realm.getDefaultInstance();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        initData();
//        setContentView(R.layout.activity_questions_list);
////        ButterKnife.bind(this);
////        initView();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        realm.close();
//    }
//
//    private void initData() {
//        String title = getIntent().getStringExtra("paperTitle");
//        String author = getIntent().getStringExtra("paperAuthorAndDate");
//        RealmResults<NormalExamPaper> papers = realm.where(NormalExamPaper.class).findAll();
//        for (NormalExamPaper paper : papers) {
//            if (title.equals(paper.getPaperInfo().getTitle()) || author.equals(paper.getPaperInfo().getAuthor())) {
//                normalExamPaper = paper;
//            }
//        }
//        questionCount.add(normalExamPaper.getFillInQuestions().size());
//        questionCount.add(normalExamPaper.getTfQuestions().size());
//        questionCount.add(normalExamPaper.getSglChoQuestions().size());
//        questionCount.add(normalExamPaper.getMultChoQuestions().size());
//        questionCount.add(normalExamPaper.getDiscussQuestions().size());
//        questionCount.add(normalExamPaper.getQuestionsCount());
//
//        typeNames.add("填空题");
//        typeNames.add("判断题");
//        typeNames.add("单选题");
//        typeNames.add("多选题");
//        typeNames.add("简答题");
//        typeNames.add("顺序学习");
//
//        typeImages.add(R.drawable.circle_background_downside);
//        typeImages.add(R.drawable.circle_background_downside);
//        typeImages.add(R.drawable.circle_background_downside);
//        typeImages.add(R.drawable.circle_background_downside);
//        typeImages.add(R.drawable.circle_background_downside);
//        typeImages.add(R.drawable.circle_background_downside);
//    }
//
////    private void initView() {
////        requestWindowFeature(Window.FEATURE_NO_TITLE);
////        mToolbar = (Toolbar) findViewById(R.id.toolbar);
////        setSupportActionBar(mToolbar);
////        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
////        setTitle(normalExamPaper.getPaperInfo().getTitle());
////        app_bar_layout = (AppBarLayout) findViewById(R.id.app_bar_layout);
////
////        mCollapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar_layout);
////
////        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                onBackPressed();
////            }
////        });
////        app_bar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
////            @Override
////            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
////                if (verticalOffset <= -head_layout.getHeight() / 2) {
////                    mCollapsingToolbarLayout.setTitle("涩郎");
////                } else {
////                    mCollapsingToolbarLayout.setTitle(" ");
////                }
////            }
////        });
////        questionTypeList.addItemDecoration(
////                new HorizontalDividerItemDecoration.Builder(this)
////                        .color(getResources().getColor(R.color.colorRecyclerViewDivider))
////                        .size(getResources().getDimensionPixelSize(R.dimen.paper_recyclerview_divider_height))
////                        .build());
////        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getApplicationContext());
////        questionTypeList.setLayoutManager(layoutManager);
////        adapter = new QuestionTypeAdapter();
////        questionTypeList.setAdapter(adapter);
////    }
//
////    private void loadBlurAndSetStatusBar() {
////        StatusBarUtil.setTranslucent(QuestionsListActivity.this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
////        Glide.with(this).load(R.mipmap.bg).bitmapTransform(new BlurTransformation(this, 100))
////                .into(new SimpleTarget<GlideDrawable>() {
////                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
////                    @Override
////                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super
////                            GlideDrawable> glideAnimation) {
////                        head_layout.setBackground(resource);
////                        root_layout.setBackground(resource);
////                    }
////                });
////
////        Glide.with(this).load(R.mipmap.bg).bitmapTransform(new BlurTransformation(this, 100))
////                .into(new SimpleTarget<GlideDrawable>() {
////                    @Override
////                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super
////                            GlideDrawable> glideAnimation) {
////                        mCollapsingToolbarLayout.setContentScrim(resource);
////                    }
////                });
////    }
//
//    private boolean isEmptyQuestionList(int position) {
//        if (normalExamPaper == null) return true;
//        switch (position) {
//            case FILLINQUESTIONLABEL:
//                if (normalExamPaper.getFillInQuestions().size() == 0) return true;
//                break;
//            case TFQUESTIONLABEL:
//                if (normalExamPaper.getTfQuestions().size() == 0) return true;
//                break;
//            case SGLCHOQUESTIONLABEL:
//                if (normalExamPaper.getSglChoQuestions().size() == 0) return true;
//                break;
//            case MULTCHOQUESTIONLABEL:
//                if (normalExamPaper.getMultChoQuestions().size() == 0) return true;
//                break;
//            case DISCUSSQUESTIONLABEL:
//                if (normalExamPaper.getDiscussQuestions().size() == 0) return true;
//                break;
//        }
//        return false;
//    }
//
//    public class QuestionTypeAdapter extends RecyclerView.Adapter {
//
//        @Override
//        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//            QuestionViewHolder holder1 = (QuestionViewHolder) holder;
//            holder1.typeName.setText(typeNames.get(holder1.getAdapterPosition()));
//            holder1.typeImage.setImageResource(typeImages.get(holder1.getAdapterPosition()));
//            holder1.questionCount.setText("共" + questionCount.get(holder1.getAdapterPosition()) + "题");
//            holder1.item.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (isEmptyQuestionList(position + 1)) {
//                        //tanchutishi
//                        Log.d("------------->", "in");
//                        return;
//                    }
//                    Intent intent = new Intent(QuestionsListActivity.this, AnswerStudyActivity.class);
//                    intent.putExtra("QuestionLabel", position + 1);
//                    startActivity(intent);
//                }
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return typeNames.size();
//        }
//
//        @Override
//        public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_question_list_recyclerview_item, parent, false);
//            return new QuestionViewHolder(view);
//        }
//
//        public class QuestionViewHolder extends RecyclerView.ViewHolder {
//            public TextView typeName;
//            public ImageView typeImage;
//            public TextView questionCount;
//            public LinearLayout item;
//
//            public QuestionViewHolder(View itemView) {
//                super(itemView);
//                item = (LinearLayout) itemView.findViewById(R.id.question_type_item);
//                typeImage = (ImageView) itemView.findViewById(R.id.type_image);
//                typeName = (TextView) itemView.findViewById(R.id.type_name);
//                questionCount = (TextView) itemView.findViewById(R.id.question_count);
//            }
//        }
//    }
}
