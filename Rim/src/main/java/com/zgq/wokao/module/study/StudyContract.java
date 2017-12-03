package com.zgq.wokao.module.study;

import com.zgq.wokao.module.IPresenter;
import com.zgq.wokao.module.IView;

/**
 * Created by zgq on 2017/12/3.
 */

public interface StudyContract {
    interface View extends IView<Presenter>{}
    interface Presenter extends IPresenter<View>{}
}
