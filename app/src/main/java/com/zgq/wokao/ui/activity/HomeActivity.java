package com.zgq.wokao.ui.activity;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.zgq.linechart.ChartView;
import com.zgq.wokao.R;
import com.zgq.wokao.action.login.LoginAction;
import com.zgq.wokao.action.paper.impl.StudySummaryAction;
import com.zgq.wokao.model.total.StudySummary;
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
        ScheduleFragment.OnScheduleFragmentListener,
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
    private QuestionsFragment questionsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        if (LoginAction.getInstance().isFirstTimeLogin()) {
            StudySummaryAction.getInstance().addStudySummary(new StudySummary());
            LoginAction.getInstance().setFirstTimeLoginFalse();
        }
        homePresenter = new HomePresenterImpl(this);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        homePresenter.showScheduleFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (currentItem) {
            case 0:
                homePresenter.showScheduleFragment();
                break;
            case 1:
                homePresenter.showPapersFragment();
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
        initLineChart();
        initContent();
    }

    private void initTabStrip() {
        tabStrip.setTitles("日程", "试卷");
        tabStrip.setTabIndex(0, true);
        tabStrip.setTitleSize(50);
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
                .withListeners(new SlideUp.Listener() {
                    @Override
                    public void onSlide(float percent) {
                        if (percent == 0.0) return;
                        ObjectAnimator.ofFloat(mainLayout, "translationY", menuLayout.getHeight() * (1 - percent / 100)).
                                setDuration(0).start();
                    }

                    @Override
                    public void onVisibilityChanged(int visibility) {
                        if (visibility == View.GONE) {

                        } else {

                        }
                    }
                })
                .withStartState(SlideUp.State.HIDDEN)
                .withStartGravity(Gravity.TOP)
                .build();
    }

    private void initFragments(){
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
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initLineChart() {
        //x轴坐标对应的数据
        List<String> xValue = new ArrayList<>();
        //y轴坐标对应的数据
        List<Integer> yValue = new ArrayList<>();
        //折线对应的数据
        Map<String, Integer> value = new HashMap<>();
        for (int i = 0; i < 9; i++) {
            if (i == 0 || i == 1) {
                xValue.add((i + 1) + "月");
                value.put((i + 1) + "月", (int) (181));//60--240
                continue;
            }
            if (i == 7 || i == 8) {
                xValue.add((i + 1) + "月");
                value.put((i + 1) + "月", (int) (160));//60--240
                continue;
            }
            xValue.add((i + 1) + "月");
            value.put((i + 1) + "月", (int) (Math.random() * 181 + 60));//60--240
        }

        for (int i = 0; i < 9; i++) {
            yValue.add(i * 60);
        }
        lineChart.setValue(value, xValue, yValue);
    }

    private void setListener() {
        menuBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        parseBtn.setOnClickListener(this);
    }

    private void initContent(){
        StudySummary studySummary = StudySummaryAction.getInstance().getStudySummary();
        totalCount.setText(""+studySummary.getStudyCount());
        String accuracy = "0";
        if (studySummary.getStudyCount() != 0){
            accuracy = String.valueOf(studySummary.getCorrectCount()/studySummary.getStudyCount());
        }else{
            accuracy = "未学习";
        }
        totalAccuracy.setText(accuracy);
    }

    @Override
    public void setSlideaMenuLayout(StudySummary studySummary) {

    }

    @Override
    public void setViewPagerScrollble(boolean scrollble) {
        viewPager.setScrollble(scrollble);
    }

    @Override
    public void hideToolBar() {
        toolbarLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showToolBar() {
        toolbarLayout.setVisibility(View.VISIBLE);
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
    public void updateSlideUp() {
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
        questionsFragment = QuestionsFragment.newInstance(paperId);
        transaction.replace(R.id.questions_frag_2,questionsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        hideToolBar();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_menu:
                homePresenter.updateSlideUp();
                break;
            case R.id.toolbar_search:
                homePresenter.goSearch();
                break;
            case R.id.toolbar_add:
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
        showToolBar();
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

    public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> fragments;

        public HomeFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
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
