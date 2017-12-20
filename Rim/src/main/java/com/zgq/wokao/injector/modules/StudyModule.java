package com.zgq.wokao.injector.modules;

import com.zgq.wokao.injector.PerActivity;
import com.zgq.wokao.module.study.LearningFragment;
import com.zgq.wokao.module.study.LearningPresenter;
import com.zgq.wokao.module.study.StudyContract;
import com.zgq.wokao.module.study.StudyPresenter;
import com.zgq.wokao.module.study.entity.StudyParams;

import dagger.Module;
import dagger.Provides;

@Module
@PerActivity
public class StudyModule {

    private static StudyParams studyParams;

    public StudyModule(){}

    public StudyModule(StudyParams params){
        this.studyParams = params;
    }

    @Provides
    public static StudyParams provideStudyParams(){
        return studyParams;
    }

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
