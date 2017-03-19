package com.zgq.wokao.ui.activity;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
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
import com.zgq.wokao.R;
import com.zgq.wokao.Util.FileUtil;
import com.zgq.wokao.action.login.LoginAction;
import com.zgq.wokao.ui.fragment.impl.PapersFragment;
import com.zgq.wokao.ui.fragment.impl.ScheduleFragment;
import com.zgq.wokao.ui.presenter.impl.HomePresenterImpl;
import com.zgq.wokao.ui.view.IHomeView;
import com.zgq.wokao.ui.widget.SlideUp;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements
        ScheduleFragment.OnScheduleFragmentListener,
        PapersFragment.OnPaperFragmentListener,
        IHomeView ,
        View.OnClickListener{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        homePresenter = new HomePresenterImpl(this);
        initView();
    }
    @Override
    protected void onStart() {
        super.onStart();
        homePresenter.showScheduleFragment();
        if (LoginAction.getInstance().isFirstTimeLogin()) {
            homePresenter.parseFromFile(FileUtil.getOrInitAppStoragePath()+"/default_1.txt");
            //LoginAction.getInstance().setFirstTimeLoginFalse();
        }
    }

    private void initView(){
        initSlideUp();
        initViewPager();
        setListener();
        initTabStrip();
    }

    private void initTabStrip(){
        tabStrip.setTitles("日程", "试卷");
        tabStrip.setTabIndex(0, true);
        tabStrip.setTitleSize(40);
        tabStrip.setStripColor(Color.TRANSPARENT);
        tabStrip.setStripWeight(10);
        tabStrip.setStripFactor(5f);
        tabStrip.setStripGravity(NavigationTabStrip.StripGravity.BOTTOM);
        tabStrip.setTypeface("fonts/typeface.ttf");
        tabStrip.setCornersRadius(3);
        tabStrip.setAnimationDuration(200);
        tabStrip.setInactiveColor(getResources().getColor(R.color.colorTransparentWhite));
        tabStrip.setActiveColor(Color.WHITE);
        tabStrip.setViewPager(viewPager);
    }

    private void initSlideUp(){
        slideUp = new SlideUp.Builder(menuLayout)
                .withListeners(new SlideUp.Listener(){
                    @Override
                    public void onSlide(float percent) {
                        if (percent == 0.0) return;
                        ObjectAnimator.ofFloat(mainLayout, "translationY", menuLayout.getHeight()*(1-percent/100)).
                                setDuration(0).start();
                    }

                    @Override
                    public void onVisibilityChanged(int visibility) {
                        if (visibility == View.GONE){

                        }else{

                        }
                    }
                })
                .withStartState(SlideUp.State.HIDDEN)
                .withStartGravity(Gravity.TOP)
                .build();
    }

    private void initViewPager(){
        ScheduleFragment schedlFragment = ScheduleFragment.newInstance("","");
        PapersFragment papersFragment = PapersFragment.newInstance();
        fragments.add(schedlFragment);
        fragments.add(papersFragment);
        viewPager.setAdapter(new HomeFragmentPagerAdapter(getSupportFragmentManager(),fragments));
    }

    private void setListener(){
        menuBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
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
        bundle.putString("paperId",paperId);
        openActivity(PaperInfoActivity.class,bundle);
    }

    @Override
    public void updateSlideUp() {
        if (slideUp.isVisible()) {
            slideUp.hide();
        }else {
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
        ((ScheduleFragment)fragments.get(0)).notifyDataChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_menu:
                homePresenter.updateSlideUp();
                break;
            case R.id.toolbar_search:
                homePresenter.goSearch();
                break;
        }
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
