package com.zgq.wokao.executor;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.zgq.wokao.data.realm.Paper.impl.PaperDaoImpl;
import com.zgq.wokao.model.paper.NormalIExamPaper;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by zgq on 2017/2/20.
 */

public class ParserService extends Service {
    public static final String TAG = "ParserService";

    private ParseListener listener;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.w(TAG, "in onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class MyBinder extends Binder{
        public ParserService getParserService(){
            return ParserService.this;
        }
    }

    public void setListener(ParseListener listener){
        this.listener = listener;
    }

    public interface ParseListener{
        public void onCompleted();
    }

    public void parsePaper(String filePath){
        ParserThread thread = new ParserThread(filePath);
        thread.setListener(new ParserThread.OnCompletedListener() {
            @Override
            public void onCompleted(final NormalIExamPaper paper) {
                if (paper == null){
                    Log.d("---->>paper null","");
                    return;
                }

                Realm realm = Realm.getInstance(new RealmConfiguration.Builder(ParserService.this).name("other").build());
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealm(paper);
                    }
                });
                listener.onCompleted();
                Intent intent = new Intent();
                intent.setAction("parse_action");
                intent.putExtra("parse_result","success");
                sendBroadcast(intent);
            }
        });
        thread.start();
    }



}
