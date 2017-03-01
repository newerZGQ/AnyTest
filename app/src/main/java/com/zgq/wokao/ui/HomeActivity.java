package com.zgq.wokao.ui;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.zgq.wokao.R;
import com.zgq.wokao.action.login.LoginAction;
import com.zgq.wokao.action.paper.impl.PaperAction;
import com.zgq.wokao.executor.ParserService;
import com.zgq.wokao.ui.fragment.impl.HomeFragment;

public class HomeActivity extends BaseActivity implements HomeFragment.OnHomeFragmentListener {

    public static final String TAG = "HomeActivity";

    private ParserService parserService;

    private HomeFragment homeFragment;

    private ParseBroadcastReceiver receiver ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        PaperAction.getInstance().setAllPaperInSche();
        homeFragment = HomeFragment.newInstance("","");
        showFragment(homeFragment);
        receiver = new ParseBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("parse_action");
        registerReceiver(receiver,filter);
        if (LoginAction.getInstance().isFirstTimeLogin()) {
            goService();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        unregisterReceiver(receiver);
    }

    private void showFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void goSearch() {
        openActivity(SearchActivity.class);
    }

    @Override
    public void goQuestionsList(String paperId) {
        Bundle bundle = new Bundle();
        bundle.putString("paperId",paperId);
        openActivity(QuestionsListActivity.class,bundle);
    }
    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            parserService = ((ParserService.MyBinder) iBinder).getParserService();

            parserService.setListener(new ParserService.ParseListener() {
                @Override
                public void onCompleted() {

                }
            });
            parserService.parsePaper("");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };



    private void goService(){
        Intent intent = new Intent(HomeActivity.this, ParserService.class);
        intent.putExtra("filePath","");
        bindService(intent,mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public class ParseBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("---->>","receiver");
            String action = intent.getAction();
            String result = intent.getStringExtra("parse_result");
            if (action != null && result != null && action.equals("parse_action") && result.equals("success")){
                Log.d("---->>","receiver in");
                if (LoginAction.getInstance().isFirstTimeLogin()){
                    PaperAction.getInstance().setAllPaperInSche();
                    LoginAction.getInstance().setFirstTimeLoginFalse();
                }
                homeFragment.notifyDataChanged();
            }
        }
    }
}
