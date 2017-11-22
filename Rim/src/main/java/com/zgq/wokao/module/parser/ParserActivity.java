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
import com.zgq.wokao.module.home.HomeContract;

import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by zgq on 2017/11/22.
 */

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
                .withFilter(Pattern.compile(".*\\.txt$")) // Filtering files and directories by file name using regexp
                .withFilterDirectories(true) // Set directories filterable (false by default)
                .withHiddenFiles(true) // Show hidden files and folders
                .start();
    }

    @Override
    public void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showParseResult(String message) {
        showToast(message);
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
