package com.zgq.wokao.module.welcome;

import com.zgq.wokao.module.BasePresenter;
import com.zgq.wokao.module.BaseView;

/**
 * Created by zhangguoqiang on 2017/11/19.
 */

public interface WelcomeContract {
    interface View extends BaseView<Presenter> {
        void setTip(String message);
    }

    interface Presenter extends BasePresenter<View> {
        void selectTip(String[] tips);
    }
}
