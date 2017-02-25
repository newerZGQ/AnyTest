package com.zgq.wokao.parser.executor;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.zgq.wokao.data.realm.Paper.PaperDataProvider;
import com.zgq.wokao.model.paper.NormalExamPaper;

import io.realm.Realm;

/**
 * Created by zgq on 2017/2/20.
 */

public class ParserService extends Service {
    public static final String TAG = "ParserService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.w(TAG, "in onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String filePath = intent.getStringExtra("filePath");
        parsePaper(filePath);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void parsePaper(String filePath){
        ParserThread thread = new ParserThread(filePath);
        thread.setListener(new ParserThread.OnCompletedListener() {
            @Override
            public void onCompleted(NormalExamPaper paper) {
                PaperDataProvider.getInstance().save(paper);
                Log.d("---->>papersize",""+PaperDataProvider.getInstance().getAllPaperInfo().size());
            }
        });
        thread.start();
    }

}
