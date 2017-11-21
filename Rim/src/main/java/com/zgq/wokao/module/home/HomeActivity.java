package com.zgq.wokao.module.home;

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
import com.orhanobut.logger.Logger;
import com.zgq.linechart.ChartView;
import com.zgq.wokao.R;
import com.zgq.wokao.entity.paper.info.Schedule;
import com.zgq.wokao.injector.components.DaggerHomeComponent;
import com.zgq.wokao.injector.modules.HomeModule;
import com.zgq.wokao.module.base.BaseActivity;
import com.zgq.wokao.widget.CustomViewPager;
import com.zgq.wokao.widget.SlideUp;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

public class HomeActivity extends BaseActivity {

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
        initViewPager();
        initTabStrip();
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
