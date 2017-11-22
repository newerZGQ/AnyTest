package com.zgq.wokao.module.parser;

import com.zgq.wokao.module.IPresenter;
import com.zgq.wokao.module.IView;

/**
 * Created by zgq on 2017/11/22.
 */

public interface ParserContract {
    interface View extends IView<Presenter> {
        void showLoading();
        void showParseResult(String message);
    }
    interface Presenter extends IPresenter<View> {
        void parseDocument(String filePath);
    }
}
