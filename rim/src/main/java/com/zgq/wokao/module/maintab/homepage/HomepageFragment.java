package com.zgq.wokao.module.maintab.homepage;

import android.support.v4.view.ViewPager;
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
        toolbarTitle.setTextColor(getResources().getColor(R.color.colorWhite));
    }
}
