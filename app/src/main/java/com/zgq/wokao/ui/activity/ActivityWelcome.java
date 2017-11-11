package com.zgq.wokao.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.zgq.wokao.R;

import com.zgq.wokao.Util.FileUtil;
import com.zgq.wokao.action.BaseAction;
import com.zgq.wokao.data.realm.Paper.impl.PaperDaoImpl;
import com.zgq.wokao.action.setting.MarketChecker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qqtheme.framework.util.StorageUtils;
import io.realm.Realm;

public class ActivityWelcome extends BaseActivity {
    @BindView(R.id.tips)
    TextView tip;

    private String[] tips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkSample();
        setContentView(R.layout.activity_activity_welcome);
        ButterKnife.bind(this);
        initData();
        int i = (int) (Math.random() * 5);
        tip.setText(tips[i]);

        TimerTask task = new TimerTask() {
            public void run() {
                Intent intent = new Intent();
                intent.setClass(ActivityWelcome.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        };
        Timer timer = new Timer(true);
        timer.schedule(task, 500);
    }

    public void onResume() {
        super.onResume();
        incWorkingCount();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void initData() {
        tips = getResources().getStringArray(R.array.welcome_tips);
    }

    /**
     * 打开次数增加
     */
    private void incWorkingCount() {
        MarketChecker.WoringCounter.increase(this);
    }

    private void checkSample(){
        File sample = new File(StorageUtils.getRootPath(this) + "/wokao/sample.txt");
        if (!sample.exists()){
            FileUtil.transAssets2SD("sample.txt", StorageUtils.getRootPath(this) + "/wokao/sample.txt");
        }
    }


}
