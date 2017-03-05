package com.zgq.wokao.ui.activity;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.zgq.wokao.R;
import com.zgq.wokao.Util.FileUtil;
import com.zgq.wokao.action.login.LoginAction;
import com.zgq.wokao.ui.fragment.impl.ScheduleFragment;
import com.zgq.wokao.ui.presenter.impl.HomePresenterImpl;
import com.zgq.wokao.ui.view.IHomeView;
import com.zgq.wokao.ui.widget.SlideUp;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements ScheduleFragment.OnScheduleFragmentListener, IHomeView ,View.OnClickListener{

    public static final String TAG = "HomeActivity";

    private HomePresenterImpl homePresenter;

    private ScheduleFragment schedlFragment;

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
    @BindView(R.id.toolbar_title)
    TextView titleTv;
    @BindView(R.id.home_tab)
    NavigationTabStrip tabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        schedlFragment = ScheduleFragment.newInstance("","");
        homePresenter = new HomePresenterImpl(this);
        initView();
    }
    @Override
    protected void onStart() {
        super.onStart();
        homePresenter.showScheduleFragment();
        if (LoginAction.getInstance().isFirstTimeLogin()) {
            Log.d("----->>",TAG+"first login");
            homePresenter.parseFromFile(FileUtil.getOrInitAppStoragePath()+"/default_1.txt");
//            LoginAction.getInstance().setFirstTimeLoginFalse();
        }
    }

    private void initView(){
        initTabStrip();
        initSlideUp();
        setListener();
    }

    private void initTabStrip(){
        tabStrip.setTitles("日程", "试卷");
        tabStrip.setTabIndex(0, true);
        tabStrip.setTitleSize(40);
        tabStrip.setStripColor(Color.RED);
        tabStrip.setStripWeight(10);
        tabStrip.setStripFactor(5f);
        tabStrip.setStripType(NavigationTabStrip.StripType.LINE);
        tabStrip.setStripGravity(NavigationTabStrip.StripGravity.BOTTOM);
        tabStrip.setTypeface("fonts/typeface.ttf");
        tabStrip.setCornersRadius(3);
        tabStrip.setAnimationDuration(200);
        tabStrip.setInactiveColor(Color.GRAY);
        tabStrip.setActiveColor(Color.WHITE);
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
                            titleTv.setText("试卷工厂");
                        }else{
                            titleTv.setText("设置");
                        }
                    }
                })
                .withStartState(SlideUp.State.HIDDEN)
                .withStartGravity(Gravity.TOP)
                .build();
    }

    private void setListener(){
        menuBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
    }

    private void showFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
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
        openActivity(QuestionsListActivity.class,bundle);
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
        showFragment(schedlFragment);
    }

    @Override
    public void showPapersFragment() {

    }

    @Override
    public void showFragmetn(String fragmentTag) {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void notifyDataChanged() {
        schedlFragment.notifyDataChanged();
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
}
