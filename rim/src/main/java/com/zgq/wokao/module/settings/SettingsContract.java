package com.zgq.wokao.module.settings;

import com.zgq.wokao.module.IPresenter;
import com.zgq.wokao.module.IView;

public interface SettingsContract {
    interface MainView extends IView<MainPresenter>{

    }
    interface MainPresenter extends IPresenter<MainView>{

    }

    interface SettingsView extends IView<SettingsPresenter>{

    }

    interface SettingsPresenter extends IPresenter<SettingsView>{

    }
}
