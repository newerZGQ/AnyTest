package com.zgq.wokao.ui;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.zgq.wokao.R;
import com.zgq.wokao.action.paper.impl.PaperAction;
import com.zgq.wokao.executor.ParserService;
import com.zgq.wokao.model.paper.NormalIExamPaper;
import com.zgq.wokao.model.paper.info.ExamPaperInfo;
import com.zgq.wokao.ui.fragment.impl.HomeFragment;

public class HomeActivity extends BaseActivity implements HomeFragment.OnHomeFragmentListener {

    public static final String TAG = "HomeActivity";

    private ParserService parserService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        HomeFragment homeFragment = HomeFragment.newInstance("","");
        showFragment(homeFragment);
        goService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
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

                    Log.d("----->","success");
                }
            });
            parserService.parsePaper("");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private void savePaper(NormalIExamPaper paper){
        NormalIExamPaper tmp = new NormalIExamPaper();
        tmp.setPaperInfo(paper.getPaperInfo());
        tmp.setMultChoQuestions(paper.getMultChoQuestions());
        tmp.setSglChoQuestions(paper.getSglChoQuestions());
        tmp.setDiscussQuestions(paper.getDiscussQuestions());
        tmp.setFillInQuestions(paper.getFillInQuestions());
        tmp.setTfQuestions(paper.getTfQuestions());
        PaperAction.getInstance().addExamPaper(tmp);
    }

    private void goService(){
        Intent intent = new Intent(HomeActivity.this, ParserService.class);
        intent.putExtra("filePath","");
        bindService(intent,mServiceConnection, Context.BIND_AUTO_CREATE);
    }
}
