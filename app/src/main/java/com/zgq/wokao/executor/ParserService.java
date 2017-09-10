package com.zgq.wokao.executor;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.zgq.wokao.model.paper.NormalExamPaper;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by zgq on 2017/2/20.
 */

public class ParserService extends Service {
    public static final String TAG = "ParserService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        String filePath = intent.getStringExtra("filePath");
        parsePaper(filePath);
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class MyBinder extends Binder {
        public ParserService getParserService() {
            return ParserService.this;
        }
    }

    public void parsePaper(String filePath) {
        ParserThread thread = new ParserThread(filePath);
        thread.setListener(new ParserThread.OnCompletedListener() {
            @Override
            public void onCompleted(final NormalExamPaper paper) {
                if (paper == null) {
                    Log.d("---->>paper null", "");
                    return;
                }

                Realm realm = Realm.getInstance(new RealmConfiguration.Builder(ParserService.this).name("other").build());
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealm(paper);
                    }
                });
                Intent intent = new Intent();
                intent.setAction("parse_action");
                intent.putExtra("parse_result", "success");
                intent.putExtra("paperId", paper.getPaperInfo().getId());
                sendBroadcast(intent);
            }
        });
        thread.start();
    }
}
