package com.zgq.wokao.action.parser;


import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.zgq.wokao.Util.ContextUtil;
import com.zgq.wokao.action.login.LoginAction;
import com.zgq.wokao.action.paper.impl.PaperAction;
import com.zgq.wokao.exception.ParseException;
import com.zgq.wokao.executor.ParserService;
import com.zgq.wokao.model.paper.IExamPaper;
import com.zgq.wokao.parser.ParserHelper;
import com.zgq.wokao.ui.activity.HomeActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by zgq on 2017/2/28.
 */

public class ParserAction implements IParserAction {

    private ParserHelper parserHelper = ParserHelper.getInstance();

    private Context context;
    private ParseResultListener listener;
    private ParseBroadcastReceiver receiver;
    private ParserService parserService;

    private ParserAction(){
        init();
        registerReceiver();
    }

    public static class InstanceHolder{
        public static ParserAction instance = new ParserAction();
    }


    public static ParserAction getInstance(){
        return InstanceHolder.instance;
    }

    @Override
    public void setListener(ParseResultListener listener){
        this.listener = listener;
    }

    private void init(){
        context = ContextUtil.getContext();
    }

    private void registerReceiver(){
        receiver = new ParseBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("parse_action");
        context.registerReceiver(receiver,filter);
    }

    private void unRegisterReceiver(){
        context.unregisterReceiver(receiver);
    }


    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            parserService = ((ParserService.MyBinder) iBinder).getParserService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    @Override
    public IExamPaper parseFromFile(String fileString) throws ParseException {
        if (listener == null) return null;
        Intent intent = new Intent(context, ParserService.class);
        intent.putExtra("filePath",fileString);
        context.bindService(intent,mServiceConnection, context.BIND_AUTO_CREATE);
        return null;
    }

    @Override
    public IExamPaper parseFromIS(InputStream inputStream) {
        return null;
    }

    @Override
    public IExamPaper parseFromString(String content) {
        return null;
    }

    public class ParseBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("---->>","receiver");
            String action = intent.getAction();
            String result = intent.getStringExtra("parse_result");
            if (action != null && result != null && action.equals("parse_action") && result.equals("success")){
                Log.d("---->>","receiver in");
                if (LoginAction.getInstance().isFirstTimeLogin()){
                    PaperAction.getInstance().setAllPaperInSche();
                }
                String paperId = intent.getStringExtra("paperId");
                listener.onParseSuccess(paperId);
            }else{
                listener.onParseError("parse error");
            }
            unRegisterReceiver();
        }
    }

    public interface ParseResultListener{
        public void onParseSuccess(String paperId);
        public void onParseError(String error);
    }
}
