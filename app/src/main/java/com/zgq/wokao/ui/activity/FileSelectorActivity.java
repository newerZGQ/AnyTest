package com.zgq.wokao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.zgq.wokao.R;
import com.zgq.wokao.ui.presenter.impl.FileSelectorPresenter;
import com.zgq.wokao.ui.view.IFileSelectorView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zgq on 2017/9/11.
 */

public class FileSelectorActivity extends BaseActivity implements IFileSelectorView {

    FileSelectorPresenter presenter = new FileSelectorPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fileselector);
        new MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(1)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            showLoadingView();
            presenter.parseFromFile(filePath);
        }else{
            startHomeActivity(0);
        }
    }

    @Override
    public void notifyParseFailed() {
        showToast(getResources().getString(R.string.parse_failed));
        startHomeActivity(2000);
    }

    @Override
    public void notifyParseSuccess() {
        showToast(getResources().getString(R.string.parse_success));
        startHomeActivity(2000);
    }

    private void showLoadingView() {
        findViewById(R.id.loadView).setVisibility(View.VISIBLE);
    }

    private void startHomeActivity(int delayTime) {
        Timer timer = new Timer();
        timer.schedule(startHomeActivity, delayTime);
    }

    private TimerTask startHomeActivity = new TimerTask() {
        @Override
        public void run() {
            finish();
            openActivity(HomeActivity.class);
        }
    };
}
