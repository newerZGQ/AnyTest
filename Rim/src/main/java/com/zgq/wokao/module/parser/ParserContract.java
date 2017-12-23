package com.zgq.wokao.module.parser;

import com.zgq.wokao.module.IPresenter;
import com.zgq.wokao.module.IView;

public interface ParserContract {
    interface View extends IView<Presenter> {
        void showLoading();
        void destoryView();
        void showSuccessToast();
        void showFailedToast();
    }
    interface Presenter extends IPresenter<View> {
        void parseDocument(String filePath);
    }
}
