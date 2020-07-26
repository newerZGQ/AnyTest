package com.zgq.wokao.module.maintab;

import com.zgq.wokao.module.IPresenter;
import com.zgq.wokao.module.IView;

public interface MainTabContract {
    interface MainTabView extends IView<MainTabContract.MainTabPresenter> {
    }

    interface MainTabPresenter extends IPresenter<MainTabContract.MainTabView> {
    }

    interface HomepageView extends IView<MainTabContract.HomepagePresenter> {

    }

    interface HomepagePresenter extends IPresenter<MainTabContract.HomepageView> {

    }

    interface ManagerView extends IView<MainTabContract.ManagerPresenter> {

    }

    interface ManagerPresenter extends IPresenter<MainTabContract.ManagerView> {

    }
}
