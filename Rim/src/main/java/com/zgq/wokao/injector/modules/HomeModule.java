package com.zgq.wokao.injector.modules;

import com.zgq.wokao.module.home.PaperFragment;
import com.zgq.wokao.module.home.ScheduleFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangguoqiang on 2017/11/21.
 */

@Module
public class HomeModule {
    public HomeModule(){}

    @Provides
    public ScheduleFragment provideScheduleFragment(){
        return new ScheduleFragment();
    }

    @Provides
    public PaperFragment providePaperFragment(){
        return new PaperFragment();
    }
}
