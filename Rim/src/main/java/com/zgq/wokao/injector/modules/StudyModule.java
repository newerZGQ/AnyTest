package com.zgq.wokao.injector.modules;

import com.zgq.wokao.injector.PerActivity;
import com.zgq.wokao.module.study.StudyContract;
import com.zgq.wokao.module.study.StudyPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class StudyModule {

    @Provides
    @PerActivity
    public StudyContract.Presenter provideStudyPresenter(StudyPresenter studyPresenter){
        return studyPresenter;
    }
}
