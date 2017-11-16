package com.zgq.wokao.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.zgq.wokao.R;

import com.zgq.wokao.Util.FileUtil;
import com.zgq.wokao.action.BaseAction;
import com.zgq.wokao.action.parser.ParserAction;
import com.zgq.wokao.data.realm.Paper.impl.PaperDaoImpl;
import com.zgq.wokao.action.setting.MarketChecker;
import com.zgq.wokao.model.paper.IExamPaper;

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
    private static final int MY_PERMISSIONS_REQUEST_OPERATE_SDCARD = 972;
    @BindView(R.id.tips)
    TextView tip;

    private String[] tips;

    private boolean needToParseSample = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_welcome);
        ButterKnife.bind(this);
        initData();
        int i = (int) (Math.random() * 5);
        tip.setText(tips[i]);

        TimerTask task = new TimerTask() {
            public void run() {
                checkPermissions();
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

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            goHomeActivity();
            return;
        }
        final String[] permissions = {"Manifest.permission.WRITE_EXTERNAL_STORAGE",
                "Manifest.permission.READ_EXTERNAL_STORAGE"};
        int i = ContextCompat.checkSelfPermission(this, permissions[0]);
        if (i != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ActivityWelcome.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_OPERATE_SDCARD);
            return;
        }
        goHomeActivity();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_OPERATE_SDCARD:
                if (grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    checkSample();
                    if (needToParseSample){
                        try {
                            parseAssetsFile(Environment.getExternalStorageDirectory().getPath() + "/wokao/sample.txt");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }else {
                    showToast(getResources().getString(R.string.storage_waring));
                }
                break;
            default:
                break;
        }
    }

     private void parseAssetsFile(String filePath) throws Exception {
        ParserAction.getInstance(new ParserAction.ParseResultListener() {
            @Override
            public void onParseSuccess(String paperId) {
                goHomeActivity();
            }

            @Override
            public void onParseError(String error) {
                goHomeActivity();
            }
        }).parseFromFile(filePath);
    }

    private void goHomeActivity(){
                Intent intent = new Intent();
                intent.setClass(ActivityWelcome.this, HomeActivity.class);
                startActivity(intent);
                finish();
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
        if (sample.exists()){
            goHomeActivity();
            return;
        }
        FileUtil.transAssets2SD("sample.txt",
                StorageUtils.getRootPath(this) + "/wokao/sample.txt");
        needToParseSample = true;
    }
}
