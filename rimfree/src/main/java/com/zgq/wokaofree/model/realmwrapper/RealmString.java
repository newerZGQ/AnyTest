package com.zgq.wokaofree.model.realmwrapper;

import io.realm.RealmObject;

/**
 * Created by zgq on 2017/9/10.
 */

public class RealmString extends RealmObject {
    private String val;

    public RealmString() {
    }

    public RealmString(String val) {
        this.val = val;
    }

    public String getValue() {
        return val;
    }
}
