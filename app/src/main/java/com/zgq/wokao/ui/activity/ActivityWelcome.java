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

public class ActivityWelcome extends AppCompatActivity {
    @BindView(R.id.tips)
    TextView tip;

    private int[] tips = new int[5];

    private Realm realm = Realm.getDefaultInstance();
    private MyHandler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_welcome);
        ButterKnife.bind(this);
        initData();
        int i = (int) (Math.random() * 5);
        tip.setText(getResources().getString(tips[i]));
        if (isFirstTimeLogin()) {
            try {
//                parseExamPaperFromAsset();
            } catch (Exception e) {

            }

        }

        TimerTask task = new TimerTask() {
            public void run() {
                Intent intent = new Intent();
                intent.setClass(ActivityWelcome.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        };
        Timer timer = new Timer(true);
        timer.schedule(task,1000);
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
        tips[0] = R.string.tips1;
        tips[1] = R.string.tips2;
        tips[2] = R.string.tips3;
        tips[3] = R.string.tips4;
        tips[4] = R.string.tips5;
        myHandler = new MyHandler(this);
    }

    private boolean isFirstTimeLogin() {
        SharedPreferences sp = getSharedPreferences("setting", 0);
        boolean first = sp.getBoolean("first_time", true);
        if (first) {
            sp.edit().putBoolean("first_time", false).apply();
        }
        return first;
    }

    private void parseExamPaperFromAsset() throws Exception {
        AssetManager am = getAssets();
        InputStream inputStream = am.open("default.txt");
        File txt = createFileFromInputStream(inputStream);
        PaperDaoImpl.getInstance().parseTxt2Realm(txt, new File(StorageUtils.getRootPath(ActivityWelcome.this) + "wokao/tmp.xml"), realm, myHandler);
    }

    private File createFileFromInputStream(InputStream inputStream) {

        try {
            File f = new File(StorageUtils.getRootPath(ActivityWelcome.this) + "wokao/tmp.txt");
            OutputStream outputStream = new FileOutputStream(f);
            byte buffer[] = new byte[1024];
            int length = 0;

            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.close();
            inputStream.close();

            return f;
        } catch (IOException e) {
            //Logging exception
        }
        return null;
    }

    /**
     * 打开次数增加
     */
    private void incWorkingCount(){
        MarketChecker.WoringCounter.increase(this);
    }




    static class MyHandler extends Handler {
        WeakReference<ActivityWelcome> mWeakActivity;

        public MyHandler(ActivityWelcome activity) {
            mWeakActivity = new WeakReference<ActivityWelcome>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0X1111:
                    Toast.makeText(mWeakActivity.get(), "解析成功", Toast.LENGTH_SHORT).show();
                    break;
                case 0X1112:
                    Toast.makeText(mWeakActivity.get(), "解析错误，请检查文档标题和作者", Toast.LENGTH_SHORT).show();
                    break;
                case 0X1113:
                    Toast.makeText(mWeakActivity.get(), "解析错误，请检查文档格式", Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    }

}
