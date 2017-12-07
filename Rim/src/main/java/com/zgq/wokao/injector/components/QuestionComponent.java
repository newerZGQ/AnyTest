package com.zgq.wokao.injector.components;

import com.zgq.wokao.injector.PerActivity;
import com.zgq.wokao.injector.modules.BaseModule;
import com.zgq.wokao.injector.modules.QuestionsModule;
import com.zgq.wokao.module.question.QuestionsActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {QuestionsModule.class, BaseModule.class})
public interface QuestionComponent {
    void inject(QuestionsActivity activity);
}
