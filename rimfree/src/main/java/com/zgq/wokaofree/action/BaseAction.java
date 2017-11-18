package com.zgq.wokaofree.action;

import io.realm.Realm;

/**
 * Created by zgq on 2017/2/28.
 */

public class BaseAction implements IAction {
    private Realm realm = Realm.getDefaultInstance();

    public Realm getRealm() {
        return realm;
    }
}
