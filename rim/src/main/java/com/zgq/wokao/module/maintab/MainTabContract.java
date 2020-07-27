package com.zgq.wokao.module.maintab;

import com.zgq.wokao.module.IPresenter;
import com.zgq.wokao.module.IView;

public interface MainTabContract {
    interface MainTabView extends IView<MainTabContract.MainTabPresenter> {
    }

    interface MainTabPresenter extends IPresenter<MainTabContract.MainTabView> {
    }

    interface HomepageView extends IView<MainTabContract.HomepagePresenter> {
        void updatePaperInfo(String title, String chapter);
        void udpateAcc(String progress, String acc);
        void updateProgress(String todayNum, String totalNum, String totalDays);
    }

    interface HomepagePresenter extends IPresenter<MainTabContract.HomepageView> {
        void switchPaper();
    }

    interface ManagerView extends IView<MainTabContract.ManagerPresenter> {

    }

    interface ManagerPresenter extends IPresenter<MainTabContract.ManagerView> {

    }
}
