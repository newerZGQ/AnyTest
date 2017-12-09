package com.zgq.wokao.module.search;

import com.zgq.wokao.module.IPresenter;
import com.zgq.wokao.module.IView;

public interface SearchContract {
    interface MainView extends IView<MainPresenter>{

    }

    interface MainPresenter extends IPresenter<MainView>{

    }

    interface SearchView extends IView<SearchPresenter>{

    }

    interface SearchPresenter extends IPresenter<SearchView>{

    }
}
