package com.zgq.wokao.module.parser;

import android.content.Intent;
import android.view.View;

import com.mingle.widget.LoadingView;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.zgq.wokao.R;
import com.zgq.wokao.injector.components.DaggerParserComponent;
import com.zgq.wokao.injector.modules.ParserModule;
import com.zgq.wokao.module.base.BaseActivity;
import com.zgq.wokao.module.home.HomeActivity;

import butterknife.BindView;

public class ParserActivity extends BaseActivity<ParserContract.Presenter>
        implements ParserContract.View {

    @BindView(R.id.loadView)
    LoadingView loadingView;
    @Override
    protected void daggerInject() {
        DaggerParserComponent.builder()
                .applicationComponent(getAppComponent())
                .parserModule(new ParserModule())
                .build()
                .inject(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_parser;
    }

    @Override
    protected void initViews() {
        new MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(1)
                .withFilterDirectories(true)
                .withHiddenFiles(true)
                .start();
    }

    @Override
    public void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void destoryView() {
        openActivity(HomeActivity.class);
    }

    @Override
    public void showSuccessToast() {
        showToast(getString(R.string.parse_success));
    }

    @Override
    public void showFailedToast() {
        showToast(getString(R.string.parse_failed));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            presenter.parseDocument(filePath);
        }else{
            openActivity(HomeActivity.class);
        }
    }
}
