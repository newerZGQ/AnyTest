package com.zgq.wokaofree.action.parser;


import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.zgq.wokaofree.Util.ContextUtil;
import com.zgq.wokaofree.action.login.LoginAction;
import com.zgq.wokaofree.action.paper.impl.PaperAction;
import com.zgq.wokaofree.exception.ParseException;
import com.zgq.wokaofree.executor.ParserService;
import com.zgq.wokaofree.model.paper.IExamPaper;
import com.zgq.wokaofree.parser.ParserHelper;
import com.zgq.wokaofree.ui.activity.HomeActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by zgq on 2017/2/28.
 */

public class ParserAction implements IParserAction {

    private ParserHelper parserHelper = ParserHelper.getInstance();

    private Context context;
    private ParseResultListener listener = new ParseResultListener() {
        @Override
        public void onParseSuccess(String paperId) {

        }

        @Override
        public void onParseError(String error) {

        }
    };
    private ParseBroadcastReceiver receiver;
    private ParserService parserService;

    private ParserAction() {
        init();
    }

    public static class InstanceHolder {
        public static ParserAction instance = new ParserAction();
    }


    public static ParserAction getInstance(ParseResultListener listener) {
        InstanceHolder.instance.setListener(listener);
        return InstanceHolder.instance;
    }

    private void setListener(ParseResultListener listener) {
        this.listener = listener;
    }

    private void init() {
        context = ContextUtil.getContext();
    }

    private void registerReceiver() {
        receiver = new ParseBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("parse_action");
        context.registerReceiver(receiver, filter);
    }

    private void unRegisterReceiver() {
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
    public IExamPaper parseFromFile(String fileString) throws ParseException, Exception {
        registerReceiver();
        if (listener == null) return null;
        Intent intent = new Intent(context, ParserService.class);
        intent.putExtra("filePath", fileString);
        context.bindService(intent, mServiceConnection, context.BIND_AUTO_CREATE);
        return null;
    }

    @Override
    public IExamPaper parseFromIS(InputStream inputStream) {
//        registerReceiver();
        return null;
    }

    @Override
    public IExamPaper parseFromString(String content) {
//        registerReceiver();
        return null;
    }

    public class ParseBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String result = intent.getStringExtra("parse_result");
            String paperId = intent.getStringExtra("paperId");
            if (action != null && result != null && action.equals("parse_action") && result.equals("success")) {
                PaperAction.getInstance().addToSchedule(paperId);
                listener.onParseSuccess(paperId);
            } else {
                listener.onParseError("parse error");
            }
            unRegisterReceiver();
            context.unbindService(mServiceConnection);
        }
    }

    public interface ParseResultListener {
        void onParseSuccess(String paperId);

        void onParseError(String error);
    }
}
