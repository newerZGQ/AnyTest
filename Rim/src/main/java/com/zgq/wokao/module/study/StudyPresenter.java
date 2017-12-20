package com.zgq.wokao.module.study;

import com.zgq.wokao.module.base.BasePresenter;
import com.zgq.wokao.repository.RimRepository;

import javax.inject.Inject;

public class StudyPresenter extends BasePresenter<StudyContract.MainView> implements StudyContract.MainPresenter {

    private RimRepository repository;

    @Inject
    public StudyPresenter(RimRepository repository){
        this.repository = repository;
    }
}
