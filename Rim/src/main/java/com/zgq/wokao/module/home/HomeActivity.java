package com.zgq.wokao.module.home;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
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

import javax.inject.Inject;

import butterknife.BindView;

public class HomeActivity extends BaseActivity {

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

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_home;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerHomeComponent.builder()
                .applicationComponent(getAppComponent())
                .homeModule(new HomeModule())
                .build()
                .inject(this);
    }
}
