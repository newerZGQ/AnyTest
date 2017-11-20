package com.zgq.wokao.repository;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by zgq on 2017/11/20.
 */

public class RimRepository implements RimDataSource {

    private PaperRepository paperRepository;

    @Inject
    public RimRepository(PaperRepository paperRepository){
        this.paperRepository = paperRepository;
    }
}
