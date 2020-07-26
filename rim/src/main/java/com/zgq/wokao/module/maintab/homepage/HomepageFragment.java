package com.zgq.wokao.module.maintab.homepage;

import android.graphics.Outline;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.injector.components.DaggerMaintabComponent;
import com.zgq.wokao.injector.modules.MainTabModule;
import com.zgq.wokao.module.base.BaseFragment;
import com.zgq.wokao.module.maintab.MainTabContract;
import com.zgq.wokao.module.maintab.MainTabContract.HomepageView;
import com.zgq.wokao.widget.ScheduleInfoView;
import com.zgq.wokao.widget.TaskSettingLayout;

import butterknife.BindView;

public class HomepageFragment extends BaseFragment<MainTabContract.HomepagePresenter> implements HomepageView {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.toolbar_switch)
    TextView toolbarSwitch;

    @BindView(R.id.study_detail)
    ViewGroup studyDetail;

    @BindView(R.id.recent_tag)
    TextView recentTag;

    @BindView(R.id.start_study)
    TextView startStudy;

    @BindView(R.id.study_record)
    ViewGroup studyRecord;

    @Override
    protected void daggerInject() {
        DaggerMaintabComponent.builder()
                .applicationComponent(getAppComponent())
                .mainTabModule(new MainTabModule())
                .build()
                .inject(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_homepage;
    }

    @Override
    protected void initViews() {
        toolbarSwitch.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), view.getHeight()/2);
            }
        });
        toolbarSwitch.setClipToOutline(true);

        studyDetail.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), view.getHeight()/10);
            }
        });
        studyDetail.setClipToOutline(true);
        recentTag.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0,0,view.getWidth(), view.getHeight(), view.getHeight()/8);
            }
        });
        recentTag.setClipToOutline(true);
        startStudy.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0,0,view.getWidth(),view.getHeight(),view.getHeight()/5);
            }
        });
        startStudy.setClipToOutline(true);
        studyRecord.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0,0,view.getWidth(),view.getHeight(),view.getHeight()/10);
            }
        });
        studyRecord.setClipToOutline(true);
    }
}
