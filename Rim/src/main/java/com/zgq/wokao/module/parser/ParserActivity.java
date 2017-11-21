package com.zgq.wokao.module.parser;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.zgq.wokao.R;
import com.zgq.wokao.injector.components.DaggerParserComponent;
import com.zgq.wokao.injector.modules.ParserModule;
import com.zgq.wokao.module.base.BaseActivity;

import java.util.regex.Pattern;

import javax.inject.Inject;

/**
 * Created by zgq on 2017/11/22.
 */

public class ParserActivity extends BaseActivity<ParserContract.Presenter>
        implements ParserContract.View {

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
}
