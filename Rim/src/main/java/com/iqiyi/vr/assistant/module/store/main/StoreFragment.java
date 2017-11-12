package com.iqiyi.vr.assistant.module.store.main;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.iqiyi.vr.assistant.R;
import com.iqiyi.vr.assistant.api.bean.app.AppModel;
import com.iqiyi.vr.assistant.injector.components.DaggerStoreMainComponent;
import com.iqiyi.vr.assistant.injector.modules.StoreMainModule;
import com.iqiyi.vr.assistant.module.base.BaseFragment;
import com.iqiyi.vr.assistant.module.base.IBasePresenter;
import com.iqiyi.vr.assistant.module.base.ILoadDataView;
import com.iqiyi.vr.assistant.module.base.IPayPresenter;
import com.iqiyi.vr.assistant.util.CommonConstant;
import com.iqiyi.vr.recyclerviewhelper.adapter.BaseQuickAdapter;
import com.iqiyi.vr.recyclerviewhelper.helper.RecyclerViewHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

import static android.app.Activity.RESULT_OK;

/**
 * Created by wangyancong on 2017/8/24.
 * 应用商店主页
 */

public class StoreFragment extends BaseFragment<IPayPresenter> implements ILoadDataView<List<AppModel>> {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_app_list)
    RecyclerView rvAppList;
    @BindView(R.id.toolbar_title)
    TextView title;

    @Inject
    BaseQuickAdapter mAdapter;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_store;
    }

    @Override
    protected void initInjector() {
        DaggerStoreMainComponent.builder()
                .applicationComponent(getAppComponent())
                .storeMainModule(new StoreMainModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        initToolBar(toolbar, false);
        title.setText(getString(R.string.store_title));

        SlideInBottomAnimationAdapter animationAdapter =
                new SlideInBottomAnimationAdapter(mAdapter);
        RecyclerViewHelper.initRecyclerViewV(mContext, rvAppList, animationAdapter);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        presenter.getData(isRefresh);
    }

    @Override
    public void loadData(List<AppModel> appList) {
        mAdapter.updateItems(appList);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CommonConstant.PAY_REQUEST_CODE && resultCode == RESULT_OK) {
            long appId = data.getLongExtra(CommonConstant.RESULT_APPID_KEY, 0);
            int orderId = data.getIntExtra(CommonConstant.RESULT_ORDERID_KEY, 0);
            presenter.pushData(appId, orderId);
        }
    }
}
