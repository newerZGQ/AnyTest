package com.zgq.wokao.entity.paper.question.option;

import com.zgq.wokao.entity.CascadeDeleteable;

import io.realm.RealmObject;

/**
 * Created by zgq on 16-6-18.
 */
public class Option extends RealmObject implements CascadeDeleteable {
    private String option;
    private String tag;

    @Override
    public void cascadeDelete() {
        deleteFromRealm();
    }

}
