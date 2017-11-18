package com.zgq.wokaofree.wxapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.zgq.wokaofree.R;
import com.zgq.wokaofree.Util.WXShare;
import com.zgq.wokaofree.ui.activity.BaseActivity;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler{
    private String TAG = WXEntryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
        WXShare.handleIntent(getIntent(),this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        onBackPressed();
    }


}
