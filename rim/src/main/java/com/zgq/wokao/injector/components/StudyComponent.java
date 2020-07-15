package com.zgq.wokao.injector.components;

import com.zgq.wokao.injector.PerActivity;
import com.zgq.wokao.injector.modules.BaseModule;
import com.zgq.wokao.injector.modules.StudyModule;
import com.zgq.wokao.module.study.LearningFragment;
import com.zgq.wokao.module.study.StudyActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {StudyModule.class, BaseModule.class})
public interface StudyComponent {
    void inject(StudyActivity activity);
    void inject(LearningFragment fragment);
}
