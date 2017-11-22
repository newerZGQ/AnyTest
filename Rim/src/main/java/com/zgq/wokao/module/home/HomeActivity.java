package com.zgq.wokao.module.home;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.zgq.linechart.ChartView;
import com.zgq.wokao.R;
import com.zgq.wokao.entity.summary.StudySummary;
import com.zgq.wokao.injector.components.DaggerHomeComponent;
import com.zgq.wokao.injector.modules.HomeModule;
import com.zgq.wokao.module.base.BaseActivity;
import com.zgq.wokao.module.parser.ParserActivity;
import com.zgq.wokao.widget.CustomViewPager;
import com.zgq.wokao.widget.SlideUp;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

public class HomeActivity extends BaseActivity<HomeContract.MainPresenter>
        implements HomeContract.MainView, View.OnClickListener{

    private static final String TAG = HomeActivity.class.getSimpleName();

    @BindView(R.id.root_view)
    View rootView;
    @BindView(R.id.main_layout)
    RelativeLayout mainLayout;
    @BindView(R.id.menu_layout)
    RelativeLayout menuLayout;
    @BindView(R.id.toolbar_menu)
    Button menuBtn;
    @BindView(R.id.share)
    Button shareApp;
    @BindView(R.id.toolbar_search)
    Button searchBtn;
    @BindView(R.id.toolbar_layout)
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

    @Inject
    ScheduleFragment scheduleFragment;

    @Inject
    PaperFragment paperFragment;

    ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    protected void daggerInject() {
        DaggerHomeComponent.builder()
                .applicationComponent(getAppComponent())
                .homeModule(new HomeModule())
                .build()
                .inject(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_home;
    }

    @Override
    protected void initViews() {
        initSlideUp();
        initViewPager();
        initTabStrip();
        setListener();
    }

    private void initViewPager() {

        fragments.add(scheduleFragment);
        fragments.add(paperFragment);
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
    private void initTabStrip() {
        String[] titles = getResources().getStringArray(R.array.TabStripTitle);
        tabStrip.setTitles(titles[0], titles[1]);
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

    private void setListener() {
        menuBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        parseBtn.setOnClickListener(this);
        shareApp.setOnClickListener(this);
    }

    private void animateToolbarLeft(int duration) {
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

    private void animateToolbarRight(int duration) {
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

    private void changeSlideUpState() {
        if (slideUp.isVisible()) {
            slideUp.hide();
        } else {
            slideUp.show();
        }
    }

    @Override
    public void showDayCurve(StudySummary studySummary) {

    }

    @Override
    public void showTotalRecord(StudySummary studySummary) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_menu:
                changeSlideUpState();
                break;
            case R.id.toolbar_search:
                //goSearch();
                break;
            case R.id.toolbar_add:
                openActivity(ParserActivity.class);
                break;
            case R.id.share:
                //openActivity(SettingsActivity.class);
                break;
            default:
                break;
        }
    }

    private class HomeFragmentPagerAdapter extends FragmentPagerAdapter {
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
