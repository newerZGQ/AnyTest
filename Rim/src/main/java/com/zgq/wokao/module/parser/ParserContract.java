package com.zgq.wokao.module.parser;

import com.zgq.wokao.module.BasePresenter;
import com.zgq.wokao.module.BaseView;

/**
 * Created by zgq on 2017/11/22.
 */

public interface ParserContract {
    interface View extends BaseView<Presenter>{
        void showLoading();
        void showParseResult(String message);
    }
    interface Presenter extends BasePresenter<View>{
        void parseDocument(String filePath);
    }
}
