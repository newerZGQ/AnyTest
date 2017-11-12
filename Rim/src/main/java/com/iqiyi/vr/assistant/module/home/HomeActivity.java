package com.iqiyi.vr.assistant.module.home;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iqiyi.vr.assistant.R;
import com.iqiyi.vr.assistant.adapter.ViewPagerAdapter;
import com.iqiyi.vr.assistant.injector.components.DaggerHomeMainComponent;
import com.iqiyi.vr.assistant.injector.modules.HomeMainModule;
import com.iqiyi.vr.assistant.module.base.BaseActivity;
import com.iqiyi.vr.assistant.module.base.IBasePresenter;
import com.iqiyi.vr.assistant.module.base.ILoadDataView;
import com.iqiyi.vr.assistant.module.manager.ManagerFragment;
import com.iqiyi.vr.assistant.module.store.main.StoreFragment;
import com.iqiyi.vr.assistant.module.vip.VipFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class HomeActivity extends BaseActivity<IBasePresenter> implements ILoadDataView {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Inject
    ViewPagerAdapter pagerAdapter;

    private long exitTime = 0;

    private int[] titles = {
            R.string.tab_store_title,
            R.string.tab_vip_title,
            R.string.tab_manager_title
    };

    private int[] resDrawable = {
            R.drawable.tab_store_selector,
            R.drawable.tab_vip_selector,
            R.drawable.tab_manager_selector
    };

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_home;
    }

    @Override
    protected void initInjector() {
        DaggerHomeMainComponent.builder()
                .applicationComponent(getAppComponent())
                .homeMainModule(new HomeMainModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        presenter.getData(isRefresh);
    }

    @Override
    public void loadData(Object data) {
        initializeFragments();
        pagerAdapter.setItems(fragments, new ArrayList<String>());
        initializeTabs();
    }

    private void initializeFragments() {
        StoreFragment mStoreFragment = new StoreFragment();
        VipFragment mVipFragment = new VipFragment();
        ManagerFragment mManagerFragment = new ManagerFragment();

        fragments.add(mStoreFragment);
        fragments.add(mVipFragment);
        fragments.add(mManagerFragment);
    }

    private void initializeTabs() {
        tabLayout.getTabAt(0).setCustomView(getTabView(0));
        tabLayout.getTabAt(1).setCustomView(getTabView(1));
        tabLayout.getTabAt(2).setCustomView(getTabView(2));
    }

    private View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_tab, null);
        TextView tabTitle = view.findViewById(R.id.tab_title);
        tabTitle.setText(titles[position]);
        ImageView tabImage = view.findViewById(R.id.tab_image);
        tabImage.setImageResource(resDrawable[position]);
        return view;
    }

    @Override
    public void onBackPressed() {
        _exit();
    }

    /**
     * 退出
     */
    private void _exit() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}
