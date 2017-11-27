package com.zgq.wokao.injector.modules;

import com.zgq.wokao.injector.PerActivity;
import com.zgq.wokao.module.question.QuestionContract;
import com.zgq.wokao.module.question.QuestionPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class QuestionsModule {
    @PerActivity
    @Provides
    public QuestionContract.Presenter provideQuestionPresenter(QuestionPresenter presenter){
        return presenter;
    }
}
