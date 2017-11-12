package com.iqiyi.vr.assistant.module.store.pay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.iqiyi.vr.assistant.R;
import com.iqiyi.vr.assistant.injector.components.DaggerPayMainComponent;
import com.iqiyi.vr.assistant.injector.modules.PayMainModule;
import com.iqiyi.vr.assistant.module.base.BaseSwipeBackActivity;
import com.iqiyi.vr.assistant.module.base.IBasePresenter;
import com.iqiyi.vr.assistant.module.base.ILoadDataView;
import com.iqiyi.vr.assistant.util.CommonConstant;
import com.iqiyi.vr.assistant.widget.QYWebView;
import com.orhanobut.logger.Logger;

import butterknife.BindView;

/**
 * Created by wangyancong on 2017/8/31.
 */

public class AppPayActivity extends BaseSwipeBackActivity<IBasePresenter>
        implements ILoadDataView<String> {

    private static final String ORDER_H5_URL = "https://m.iqiyi.com/commonPay.html?partner=iqiyivr&partner_order_no=";
    private static final String APP_ID_KEY = "AppIdKey";

    @BindView(R.id.wv_pay)
    QYWebView qyWebView;

    private long appId;
    private int orderId;

    public static void launchForResult(Context context, long appId) {
        Intent intent = new Intent(context, AppPayActivity.class);
        intent.putExtra(APP_ID_KEY, appId);
        ((Activity) context).startActivityForResult(intent, CommonConstant.PAY_REQUEST_CODE);
        ((Activity) context).overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_pay;
    }

    @Override
    protected void initInjector() {
        appId = getIntent().getLongExtra(APP_ID_KEY, 0);
        DaggerPayMainComponent.builder()
                .applicationComponent(getAppComponent())
                .payMainModule(new PayMainModule(this, appId))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        qyWebView.setWebListener(new QYWebView.OnWebViewListener() {
            @Override
            public void onBack() {
                Logger.d("onBack");
                finish();
            }
        });
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        presenter.getData(isRefresh);
    }

    @Override
    public void loadData(String order) {
        if (!TextUtils.isEmpty(order))
            orderId = Integer.valueOf(order);
        qyWebView.loadUrl(ORDER_H5_URL + order);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && qyWebView.canGoBack()) {
            //判断是否第一页
            if (qyWebView.getUrl().contains(ORDER_H5_URL)) {
                finish();
            } else {
                qyWebView.goBack();//返回前一个页面
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra(CommonConstant.RESULT_APPID_KEY, appId);
        intent.putExtra(CommonConstant.RESULT_ORDERID_KEY, orderId);
        setResult(RESULT_OK, intent);
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
    }
}
