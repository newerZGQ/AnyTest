package com.zgq.wokao.module.home;

import com.zgq.wokao.module.BasePresenter;
import com.zgq.wokao.module.BaseView;

/**
 * Created by zhangguoqiang on 2017/11/12.
 */

public interface HomeContract {
    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenter<View> {}
}
