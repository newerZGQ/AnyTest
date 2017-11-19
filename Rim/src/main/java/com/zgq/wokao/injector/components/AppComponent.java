package com.zgq.wokao.injector.components;

import android.app.Application;

import com.zgq.wokao.RimApplication;
import com.zgq.wokao.injector.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface AppComponent {
    void inject(RimApplication application);

    // Gives us syntactic sugar. we can then do DaggerAppComponent.builder().application(this).build().inject(this);
    // never having to instantiate any modules or say which module we are passing the application to.
    // Application will just be provided into our app graph now.
    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
