package com.zgq.wokao.module.parser;

import com.zgq.wokao.repository.RimRepository;

import javax.inject.Inject;

/**
 * Created by zgq on 2017/11/22.
 */

public class ParserPresenter implements ParserContract.Presenter {

    @Inject
    RimRepository repository;

    private ParserContract.View view;

    @Override
    public void takeView(ParserContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        view = null;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void parseDocument(String filePath) {
        view.showLoading();
    }
}
