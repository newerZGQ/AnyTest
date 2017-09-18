package com.zgq.wokao.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.zgq.linechart.ChartView;
import com.zgq.wokao.R;
import com.zgq.wokao.action.paper.impl.StudySummaryAction;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.total.StudySummary;
import com.zgq.wokao.model.total.TotalDailyCount;
import com.zgq.wokao.ui.fragment.impl.PapersFragment;
import com.zgq.wokao.ui.fragment.impl.QuestionsFragment;
import com.zgq.wokao.ui.fragment.impl.ScheduleFragment;
import com.zgq.wokao.ui.presenter.impl.HomePresenterImpl;
import com.zgq.wokao.ui.view.IHomeView;
import com.zgq.wokao.ui.widget.CustomViewPager;
import com.zgq.wokao.ui.widget.SlideUp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements
        ScheduleFragment.ScheduleFragmentListener,
        PapersFragment.PaperFragmentListener,
        QuestionsFragment.QuestionsFragmentListener,
        IHomeView,
        View.OnClickListener {

    public static final String TAG = "HomeActivity";

    private HomePresenterImpl homePresenter;

    ArrayList<Fragment> fragments = new ArrayList<>();

    @BindView(R.id.root_view)
    View rootView;
    @BindView(R.id.main_layout)
    RelativeLayout mainLayout;
    @BindView(R.id.menu_layout)
    RelativeLayout menuLayout;
    @BindView(R.id.toolbar_menu)
    Button menuBtn;
    @BindView(R.id.toolbar_search)
    Button searchBtn;
    @BindView(R.id.toolbar_opt_layout)
    RelativeLayout toolbarLayout;
    SlideUp slideUp;
    @BindView(R.id.home_tab)
    NavigationTabStrip tabStrip;
    @BindView(R.id.viewpaper)
    CustomViewPager viewPager;
    @BindView(R.id.line_chart)
    ChartView lineChart;
    @BindView(R.id.toolbar_add)
    Button parseBtn;
    @BindView(R.id.total_count)
    TextView totalCount;
    @BindView(R.id.total_accuracy)
    TextView totalAccuracy;

    private int currentItem = 0;

    private ScheduleFragment scheduleFragment;
    private PapersFragment papersFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        StudySummaryAction.getInstance().initStudySummary();
        homePresenter = new HomePresenterImpl(this);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        showScheduleFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (currentItem) {
            case 0:
                showScheduleFragment();
                break;
            case 1:
                showPapersFragment();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        currentItem = viewPager.getCurrentItem();
    }

    private void initView() {
        initSlideUp();
        initFragments();
        initViewPager();
        setListener();
        initTabStrip();
        initLineChart(homePresenter.getDailyRecords());
        initContent();
    }

    private void initTabStrip() {
        tabStrip.setTitles("日程", "试卷");
        tabStrip.setTabIndex(0, true);
        tabStrip.setTitleSize(70);
        tabStrip.setStripColor(Color.TRANSPARENT);
        tabStrip.setStripWeight(10);
        tabStrip.setStripFactor(5f);
        tabStrip.setStripGravity(NavigationTabStrip.StripGravity.BOTTOM);
        tabStrip.setCornersRadius(3);
        tabStrip.setAnimationDuration(200);
        tabStrip.setInactiveColor(getResources().getColor(R.color.color_home_inactivity_selected_tab));
        tabStrip.setActiveColor(getResources().getColor(R.color.color_home_activity_selected_tab));
        tabStrip.setViewPager(viewPager);
    }

    private void initSlideUp() {
        slideUp = new SlideUp.Builder(menuLayout)
                .withListener(new SlideUp.Listener() {
                    @Override
                    public void onSlide(float percent) {
                    }

                    @Override
                    public void onVisibilityChanged(int visibility) {

                    }

                    @Override
                    public void onAnimatorStarted(int direction) {
                        if (direction == SlideUp.toUp) {
                            ObjectAnimator.ofFloat(mainLayout, "translationY",
                                    menuLayout.getHeight(), 0).
                                    setDuration(300).start();
                            animateToolbarRight(350);
                        } else {
                            ObjectAnimator.ofFloat(mainLayout, "translationY",
                                    0, menuLayout.getHeight()).
                                    setDuration(300).start();
                            animateToolbarLeft(350);
                        }
                    }
                })
                .withStartState(SlideUp.State.HIDDEN)
                .withStartGravity(Gravity.TOP)
                .build();
    }

    private void initFragments() {
        scheduleFragment = ScheduleFragment.newInstance("", "");
        papersFragment = PapersFragment.newInstance();
    }

    private void initViewPager() {
        fragments.add(scheduleFragment);
        fragments.add(papersFragment);
        viewPager.setAdapter(new HomeFragmentPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        parseBtn.setVisibility(View.GONE);
                        searchBtn.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        parseBtn.setVisibility(View.VISIBLE);
                        searchBtn.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initLineChart(List<TotalDailyCount> dailyRecords) {
        dailyRecords.add(0, dailyRecords.get(0));
        dailyRecords.add(dailyRecords.get(dailyRecords.size() - 1));
        List<String> xValue = new ArrayList<>();
        List<Integer> yValue = new ArrayList<>();
        Map<String, Integer> value = new HashMap<>();
        for (int i = 0; i < dailyRecords.size(); i++) {
            xValue.add("" + i);
            value.put(xValue.get(i), dailyRecords.get(i).getDailyCount());
        }

        for (int i = 0; i < 9; i++) {
            yValue.add(i * 60);
        }
        lineChart.setValue(value, xValue, yValue, 350);
    }

    private void setListener() {
        menuBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        parseBtn.setOnClickListener(this);
    }

    private void initContent() {
        StudySummary studySummary = StudySummaryAction.getInstance().getStudySummary();
        totalCount.setText(String.valueOf(studySummary.getStudyCount()));
        totalAccuracy.setText(String.valueOf(studySummary.getCorrectCount()));
    }

    @Override
    public void setViewPagerScrollble(boolean scrollble) {
        viewPager.setScrollble(scrollble);
    }

    @Override
    public void hideToolBar(int duration) {
        ObjectAnimator hideTooBar = ObjectAnimator.ofFloat(toolbarLayout, "rotationX",
                0, 90).setDuration(300);
        hideTooBar.start();
    }

    @Override
    public void showToolBar(int duration) {
        ObjectAnimator showTooBar = ObjectAnimator.ofFloat(toolbarLayout, "rotationX",
                90, 0).setDuration(300);
        showTooBar.start();
    }

    @Override
    public void animateToolbarLeft(int duration) {
        AnimatorSet hideSchedule = new AnimatorSet();
        ObjectAnimator moveScheduleTab = ObjectAnimator.ofFloat(tabStrip, "translationX",
                0, -tabStrip.getLeft() + menuBtn.getRight());
        ObjectAnimator alphaSchedule = ObjectAnimator.ofFloat(tabStrip, "alpha",
                1, 0);

        View actionLayout = (View) searchBtn.getParent();
        ObjectAnimator moveScheduleSea = ObjectAnimator.ofFloat(actionLayout, "translationX",
                0, -actionLayout.getLeft() + menuBtn.getRight());
        ObjectAnimator alphaScheduleSea = ObjectAnimator.ofFloat(actionLayout, "alpha",
                1, 0);
        hideSchedule.playTogether(moveScheduleTab, alphaSchedule, moveScheduleSea, alphaScheduleSea);
        hideSchedule.setDuration(duration);
        hideSchedule.setStartDelay(300);

        tabStrip.setClickable(false);
        searchBtn.setClickable(false);
        parseBtn.setClickable(false);

        hideSchedule.start();
    }

    @Override
    public void animateToolbarRight(int duration) {
        AnimatorSet hideSchedule = new AnimatorSet();
        ObjectAnimator moveScheduleTab = ObjectAnimator.ofFloat(tabStrip, "translationX",
                -tabStrip.getLeft() + menuBtn.getRight(), 0);
        ObjectAnimator alphaSchedule = ObjectAnimator.ofFloat(tabStrip, "alpha",
                0, 1);

        View actionLayout = (View) searchBtn.getParent();
        ObjectAnimator moveScheduleSea = ObjectAnimator.ofFloat(actionLayout, "translationX",
                -actionLayout.getLeft() + menuBtn.getRight(), 0);
        ObjectAnimator alphaScheduleSea = ObjectAnimator.ofFloat(actionLayout, "alpha",
                0, 1);
        hideSchedule.playTogether(moveScheduleTab, alphaSchedule, moveScheduleSea, alphaScheduleSea);
        hideSchedule.setDuration(duration);
        hideSchedule.setStartDelay(300);

        tabStrip.setClickable(true);
        searchBtn.setClickable(true);
        parseBtn.setClickable(true);

        hideSchedule.start();
    }

    @Override
    public void goSearch() {
        openActivity(SearchActivity.class);
    }

    @Override
    public void goQuestionsList(String paperId) {
        showQuestionsFragment(paperId);
    }

    @Override
    public void startFromScheduleFrag(String paperId, QuestionType type, int qstNum) {
        startStudy(paperId, type, qstNum, true);
    }

    @Override
    public void onStartSettingDaily() {
        hideToolBar(300);
    }

    @Override
    public void onEndSettingDaily() {
        showToolBar(300);
    }

    @Override
    public void changeSlideUpState() {
        if (slideUp.isVisible()) {
            slideUp.hide();
        } else {
            slideUp.show();
        }
    }

    @Override
    public void showScheduleFragment() {
        viewPager.setCurrentItem(0);
    }

    @Override
    public void showPapersFragment() {
        viewPager.setCurrentItem(1);
    }


    @Override
    public void showQuestionsFragment(String paperId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        QuestionsFragment questionsFragment = QuestionsFragment.newInstance(paperId);
        transaction.replace(R.id.questions_frag_2, questionsFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
        hideToolBar(300);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_menu:
                changeSlideUpState();
                break;
            case R.id.toolbar_search:
                goSearch();
                break;
            case R.id.toolbar_add:
                finish();
                openActivity(FileSelectorActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (slideUp.isVisible()) {
            slideUp.hide();
            return;
        }
        showToolBar(300);
        super.onBackPressed();
    }

    @Override
    public void onPaperDeleted(String paperId) {
        scheduleFragment.onPaperDataChanged();
    }

    @Override
    public void onPaperExitSchedule(String paperId) {
        scheduleFragment.onPaperDataChanged();
    }

    @Override
    public void onPaperInSchedule(String paperId) {
        scheduleFragment.onPaperDataChanged();
    }

    @Override
    public void startFromQuestionFrag(String paperId, QuestionType type) {
        startStudy(paperId, type, 0, true);
    }


    public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> fragments;

        HomeFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }
}
