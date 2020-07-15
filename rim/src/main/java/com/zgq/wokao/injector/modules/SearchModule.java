package com.zgq.wokao.injector.modules;

import com.zgq.wokao.injector.PerActivity;
import com.zgq.wokao.module.search.SearchContract;
import com.zgq.wokao.module.search.SearchFragment;
import com.zgq.wokao.module.search.SearchMainPresenter;
import com.zgq.wokao.module.search.SearchPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {
    public SearchModule(){}

    @Provides
    @PerActivity
    public SearchContract.MainPresenter providesSearchMainPresenter(SearchMainPresenter presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    public SearchContract.SearchPresenter providesSearchPresenter(SearchPresenter presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    public SearchFragment providesSearchFragment(){
        return new SearchFragment();
    }
}
