package com.zgq.wokao.module.parser;

import com.zgq.wokao.module.base.BasePresenter;
import com.zgq.wokao.repository.RimRepository;

import javax.inject.Inject;

public class ParserPresenter extends BasePresenter<ParserContract.View>
        implements ParserContract.Presenter {

    @Inject
    RimRepository repository;

    @Override
    public void parseDocument(String filePath) {
        view.showLoading();
    }
}
