package com.zgq.wokao.repository;

import javax.inject.Inject;

/**
 * Created by zgq on 2017/11/20.
 */

public class ResourcesRepository implements ResourcesDataSource {

    @Inject
    public ResourcesRepository(){}

    @Override
    public int getColor(int colorId) {
        return 100;
    }

    @Override
    public int getString(int stringId) {
        return 100;
    }
}
