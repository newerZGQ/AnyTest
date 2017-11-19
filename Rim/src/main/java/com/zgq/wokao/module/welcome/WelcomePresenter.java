package com.zgq.wokao.module.welcome;

import android.util.Log;

import com.zgq.wokao.repository.RimDataSource;
import com.zgq.wokao.repository.RimRepository;

import javax.inject.Inject;

/**
 * Created by zhangguoqiang on 2017/11/19.
 */

public class WelcomePresenter implements WelcomeContract.Presenter {
    private static final String TAG = WelcomePresenter.class.getSimpleName();

    private WelcomeContract.View view;
    private RimRepository repository;

    @Inject
    public WelcomePresenter(RimRepository repository){
        this.repository = repository;
    }

    @Override
    public void takeView(WelcomeContract.View view) {
        this.view = view;
        Log.d(TAG,"clolor is" + repository.getColor(0));
    }

    @Override
    public void dropView() {
        view = null;
    }

    @Override
    public void selectTip(String[] tips) {
        int i = (int) (Math.random() * 5);
        view.setTip(tips[i]);
    }
}
