package com.zgq.wokao.repository;

import javax.inject.Inject;

/**
 * Created by zgq on 2017/11/20.
 */

public class RimRepository implements RimDataSource {

    private PaperRepository paperRepository;

    private ResourcesRepository resourcesRepository;
    @Inject
    public RimRepository(PaperRepository paperRepository,ResourcesRepository resourcesRepository){
        this.paperRepository = paperRepository;
        this.resourcesRepository = resourcesRepository;
    }

    @Override
    public int getColor(int colorId) {
        return resourcesRepository.getColor(colorId);
    }

    @Override
    public int getString(int stringId) {
        return resourcesRepository.getString(stringId);
    }
}
