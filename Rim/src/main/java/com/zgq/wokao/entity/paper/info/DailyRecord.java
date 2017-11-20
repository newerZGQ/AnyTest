package com.zgq.wokao.entity.paper.info;

import com.zgq.wokao.entity.CascadeDeleteable;

import io.realm.RealmObject;

/**
 * Created by zgq on 2017/2/25.
 */
public class DailyRecord extends RealmObject implements CascadeDeleteable {
    private String date;
    private int studyCount;

    @Override
    public void cascadeDelete() {

    }
}
