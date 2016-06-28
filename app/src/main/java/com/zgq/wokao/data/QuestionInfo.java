package com.zgq.wokao.data;

import io.realm.RealmObject;

/**
 * Created by zgq on 16-6-19.
 */
public interface QuestionInfo {
    public void setType(String type);
    public String getType();
    public void setId(int id);
    public int getId();
}
