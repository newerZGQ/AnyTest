package com.iqiyi.vr.assistant.module.store.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.iqiyi.vr.assistant.R;
import com.iqiyi.vr.assistant.api.bean.app.AppModel;
import com.iqiyi.vr.assistant.api.bean.app.DetailResponse;
import com.iqiyi.vr.assistant.injector.components.DaggerDetailMainComponent;
import com.iqiyi.vr.assistant.injector.modules.DetailMainModule;
import com.iqiyi.vr.assistant.module.base.BaseSwipeBackActivity;
import com.iqiyi.vr.assistant.module.base.ILoadDataView;
import com.iqiyi.vr.assistant.module.base.IPayPresenter;
import com.iqiyi.vr.assistant.module.store.pay.AppPayActivity;
import com.iqiyi.vr.assistant.util.CommonConstant;
import com.iqiyi.vr.assistant.util.DefIconFactory;
import com.iqiyi.vr.assistant.util.ImageLoader;
import com.iqiyi.vr.assistant.util.MeasureUtil;
import com.iqiyi.vr.recyclerviewhelper.adapter.BaseQuickAdapter;
import com.iqiyi.vr.recyclerviewhelper.helper.RecyclerViewHelper;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

/**
 * Created by wangyancong on 2017/8/31.
 */

public class AppDetailActivity extends BaseSwipeBackActivity<IPayPresenter>
        implements ILoadDataView<DetailResponse> {

    private static final String APP_ID_KEY = "AppIdKey";
    private static final String APP_STATE_KEY = "AppStateKey";
    private static final String APP_PKG_KEY = "AppPkgKey";
    private static final String APP_VER_KEY = "AppVerKey";

    private long appId;
    private int appState;
    private String appPkgName;
    private int appVer;

    @Inject
    BaseQuickAdapter adapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView title;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_mouse)
    ImageView ivMouse;
    @BindView(R.id.iv_handle)
    ImageView ivHandle;
    @BindView(R.id.iv_control)
    ImageView ivControl;
    @BindView(R.id.tv_app_size)
    TextView tvAppSize;
    @BindView(R.id.tv_download_count)
    TextView tvDownloadCount;
    @BindView(R.id.btn_pay)
    Button btnPay;
    @BindView(R.id.rv_capture)
    RecyclerView rvCaptureList;
    @BindView(R.id.tv_dev_content)
    TextView tvDevContent;
    @BindView(R.id.tv_ver_content)
    TextView tvVerContent;
    @BindView(R.id.tv_size_content)
    TextView tvSizeContent;
    @BindView(R.id.tv_sup_content)
    TextView tvSupContent;
    @BindView(R.id.tv_intro_content)
    TextView tvIntroContent;

    public static void launch(Context context, long appId,
                              int appState, String appPkgName, int appVer) {
        Intent intent = new Intent(context, AppDetailActivity.class);
        intent.putExtra(APP_ID_KEY, appId);
        intent.putExtra(APP_STATE_KEY, appState);
        intent.putExtra(APP_PKG_KEY, appPkgName);
        intent.putExtra(APP_VER_KEY, appVer);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initInjector() {
        appId = getIntent().getLongExtra(APP_ID_KEY, 0);
        appState = getIntent().getIntExtra(APP_STATE_KEY, 0);
        appPkgName = getIntent().getStringExtra(APP_PKG_KEY);
        appVer = getIntent().getIntExtra(APP_VER_KEY, 0);

        DaggerDetailMainComponent.builder()
                .applicationComponent(getAppComponent())
                .detailMainModule(new DetailMainModule(this, appId, appPkgName, appVer))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        initToolBar(toolbar, true);
        SlideInBottomAnimationAdapter slideAdapter = new SlideInBottomAnimationAdapter(adapter);
        RecyclerViewHelper.initRecyclerViewSV(this, rvCaptureList, slideAdapter, 2);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        presenter.getData(isRefresh);
    }

    @Override
    public void loadData(DetailResponse data) {
        AppModel app = data.getData().getBasic();
        title.setText(app.getApp_name());
        tvName.setText(app.getApp_name() + " : " + app.getApp_desc());
        ImageLoader.loadCenterCrop(this, app.getApp_logo(), ivLogo, DefIconFactory.provideIcon());
        loadVrInteractiveWays(app.getVr_interactive_ways());
        tvAppSize.setText(loadSize(app.getApp_package_size()));
        tvDownloadCount.setText(app.getTotal_download() + getString(R.string.download_count));
        adapter.updateItems(data.getData().getRes());
        tvDevContent.setText(app.getDeveloper_name());
        tvVerContent.setText(app.getApp_ver_name());
        tvSizeContent.setText(loadSize(app.getApp_package_size()));
        tvIntroContent.setText(app.getApp_desc_detail());
    }

    private void loadVrInteractiveWays(String vrInteractiveWays) {
        //是否支持空鼠
        if (vrInteractiveWays.contains(String.valueOf(1))) {
            ivMouse.setBackgroundResource(R.drawable.ic_support_mouse);
            tvSupContent.append(getString(R.string.mouse));
        } else {
            ivMouse.setBackgroundResource(R.drawable.ic_unsupport_mouse);
        }
        //是否支持头控
        if (vrInteractiveWays.contains(String.valueOf(2))) {
            ivControl.setBackgroundResource(R.drawable.ic_support_control);
            tvSupContent.append(getString(R.string.control));
        } else {
            ivControl.setBackgroundResource(R.drawable.ic_unsupport_control);
        }
        //是否支持手柄
        if (vrInteractiveWays.contains(String.valueOf(3))) {
            ivHandle.setBackgroundResource(R.drawable.ic_support_handle);
            tvSupContent.append(getString(R.string.handle));
        } else {
            ivHandle.setBackgroundResource(R.drawable.ic_unsupport_handle);
        }
    }

    private String loadSize(long size) {
        return MeasureUtil.convertSize(size) + getString(R.string.size_m);
    }

    @OnClick(R.id.btn_pay)
    public void onClick(View view) {
        AppPayActivity.launchForResult(this, appId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CommonConstant.PAY_REQUEST_CODE && resultCode == RESULT_OK) {
            long appId = data.getLongExtra(CommonConstant.RESULT_APPID_KEY, 0);
            int orderId = data.getIntExtra(CommonConstant.RESULT_ORDERID_KEY, 0);
            Logger.d("appId ==" + appId + "orderId ==" + orderId);
            presenter.pushData(appId, orderId);
        }
    }
}
