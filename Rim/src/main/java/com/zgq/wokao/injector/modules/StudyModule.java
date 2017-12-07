package com.zgq.wokao.injector.modules;

import com.zgq.wokao.injector.PerActivity;
import com.zgq.wokao.module.study.LearningFragment;
import com.zgq.wokao.module.study.LearningPresenter;
import com.zgq.wokao.module.study.StudyContract;
import com.zgq.wokao.module.study.StudyPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class StudyModule {

    @Provides
    @PerActivity
    public StudyContract.MainPresenter provideStudyPresenter(StudyPresenter studyPresenter){
        return studyPresenter;
    }

    @Provides
    @PerActivity
    public StudyContract.LearningPresenter provideLearningPresenter(LearningPresenter learningPresenter){
        return learningPresenter;
    }

    @Provides
    @PerActivity
    public LearningFragment provideLearningFragment(){
        return new LearningFragment();
    }
}
