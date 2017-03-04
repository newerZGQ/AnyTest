package com.zgq.wokao.ui.activity;

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
import com.zgq.wokao.Util.FileUtil;
import com.zgq.wokao.action.login.LoginAction;
import com.zgq.wokao.action.paper.impl.PaperAction;
import com.zgq.wokao.executor.ParserService;
import com.zgq.wokao.ui.fragment.impl.HomeFragment;
import com.zgq.wokao.ui.presenter.impl.HomePresenterImpl;
import com.zgq.wokao.ui.view.IHomeView;

public class HomeActivity extends BaseActivity implements HomeFragment.OnHomeFragmentListener , IHomeView {

    public static final String TAG = "HomeActivity";

    private HomePresenterImpl homePresenter;

    private ParserService parserService;

    private HomeFragment homeFragment;

//    private ParseBroadcastReceiver receiver ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        homeFragment = HomeFragment.newInstance("","");
        showFragment(homeFragment);
        homePresenter.showScheduleFragment();
        if (LoginAction.getInstance().isFirstTimeLogin()) {
            homePresenter.parseFromFile(FileUtil.getOrInitAppStoragePath()+"/default_1.txt");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unbindService(mServiceConnection);
//        unregisterReceiver(receiver);
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



//    private void goService(){
//        Intent intent = new Intent(HomeActivity.this, ParserService.class);
//        intent.putExtra("filePath","");
//        bindService(intent,mServiceConnection, BIND_AUTO_CREATE);
//    }

    @Override
    public void showSlideUp() {

    }

    @Override
    public void hideSlideUp() {

    }

    @Override
    public void goSerach() {

    }

    @Override
    public void showScheduleFragment() {

    }

    @Override
    public void showPapersFragment() {

    }

    @Override
    public void showFragmetn(String fragmentTag) {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void notifyDataChanged() {

    }

//    public class ParseBroadcastReceiver extends BroadcastReceiver{
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Log.d("---->>","receiver");
//            String action = intent.getAction();
//            String result = intent.getStringExtra("parse_result");
//            if (action != null && result != null && action.equals("parse_action") && result.equals("success")){
//                Log.d("---->>","receiver in");
//                if (LoginAction.getInstance().isFirstTimeLogin()){
//                    PaperAction.getInstance().setAllPaperInSche();
//                    LoginAction.getInstance().setFirstTimeLoginFalse();
//                }
//                homeFragment.notifyDataChanged();
//            }
//        }
//    }
}
