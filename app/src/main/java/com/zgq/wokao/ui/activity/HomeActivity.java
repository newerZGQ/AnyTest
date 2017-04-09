package com.zgq.wokao.ui.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.zgq.linechart.ChartView;
import com.zgq.wokao.R;
import com.zgq.wokao.Util.FileUtil;
import com.zgq.wokao.action.login.LoginAction;
import com.zgq.wokao.action.paper.impl.StudySummaryAction;
import com.zgq.wokao.data.realm.Paper.impl.PaperDaoImpl;
import com.zgq.wokao.model.total.StudySummary;
import com.zgq.wokao.ui.fragment.impl.PapersFragment;
import com.zgq.wokao.ui.fragment.impl.ScheduleFragment;
import com.zgq.wokao.ui.presenter.impl.HomePresenterImpl;
import com.zgq.wokao.ui.view.IHomeView;
import com.zgq.wokao.ui.widget.SlideUp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qqtheme.framework.util.StorageUtils;

public class HomeActivity extends BaseActivity implements
        ScheduleFragment.OnScheduleFragmentListener,
        PapersFragment.OnPaperFragmentListener,
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
    SlideUp slideUp;
    @BindView(R.id.home_tab)
    NavigationTabStrip tabStrip;
    @BindView(R.id.viewpaper)
    ViewPager viewPager;
    @BindView(R.id.line_chart)
    ChartView lineChart;
    @BindView(R.id.toolbar_add)
    Button parseBtn;
    @BindView(R.id.loadingview)
    RelativeLayout loadingView;
    @BindView(R.id.total_count)
    TextView totalCount;
    @BindView(R.id.total_accuracy)
    TextView totalAccuracy;

    private int currentItem = 0;

    private Timer timer = new Timer();
    private TimerTask hideLoadingTask;

    public boolean needUpdateData = false;

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
        initTask();
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
        initViewPager();
        setListener();
        initTabStrip();
        initLineChart();
        initContent();
    }

    private void initTask() {
        hideLoadingTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {      // UI thread
                    @Override
                    public void run() {
                        ObjectAnimator animator = ObjectAnimator.ofFloat(loadingView, "alpha", 1, 0).setDuration(500);
                        animator.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                                loadingView.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {

                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        });
                        animator.start();
                    }
                });
            }
        };
    }

    private void initTabStrip() {
        tabStrip.setTitles("日程", "试卷");
        tabStrip.setTabIndex(0, true);
        tabStrip.setTitleSize(50);
        tabStrip.setStripColor(Color.TRANSPARENT);
        tabStrip.setStripWeight(10);
        tabStrip.setStripFactor(5f);
        tabStrip.setStripGravity(NavigationTabStrip.StripGravity.BOTTOM);
        //tabStrip.setTypeface("fonts/typeface.ttf");
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

    private void initViewPager() {
        final ScheduleFragment schedlFragment = ScheduleFragment.newInstance("", "");
        final PapersFragment papersFragment = PapersFragment.newInstance();
        fragments.add(schedlFragment);
        fragments.add(papersFragment);
        viewPager.setAdapter(new HomeFragmentPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (!needUpdateData) {
                    return;
                }
                switch (position) {
                    case 0:
                        if (positionOffset == 0) {
                            showLoadingView();
                            schedlFragment.notifyDataChanged();
                            hideLoadingView();
                            needUpdateData = false;
                        }
                        break;
                    case 1:
                        if (positionOffset == 0) {
//                            papersFragment.getPapersPresenter().notifyDataChanged();
                        }
                        break;
                }
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
    public void setNeedUpdateData(boolean needUpdateData) {
        this.needUpdateData = needUpdateData;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void goSearch() {
        openActivity(SearchActivity.class);
    }

    @Override
    public void goQuestionsList(String paperId) {
        Bundle bundle = new Bundle();
        bundle.putString("paperId", paperId);
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
    public void showFragment(String fragmentTag) {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void notifyDataChanged() {
        ((ScheduleFragment) fragments.get(0)).notifyDataChanged();
        ((PapersFragment) fragments.get(1)).getPapersPresenter().notifyDataChanged();
    }


    @Override
    public void hideLoadingView() {
        initTask();
        timer.schedule(hideLoadingTask, 1000);
    }

    @Override
    public void showLoadingView() {
        loadingView.setVisibility(View.VISIBLE);
        ObjectAnimator.ofFloat(loadingView,"alpha",0,1).setDuration(500).start();
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
                new MaterialFilePicker()
                        .withActivity(this)
                        .withRequestCode(1)
                        .start();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (slideUp.isVisible()) {
            slideUp.hide();
            return;
        }
        super.onBackPressed();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            if (filePath == null || filePath.equals("")) {
                return;
            }
            showLoadingView();
            homePresenter.parseFromFile(filePath);
        }
    }
}
