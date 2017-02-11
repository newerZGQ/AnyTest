package com.zgq.wokao.model;

import io.realm.RealmObject;

/**
 * Created by zgq on 16-6-18.
 */
public class Option extends RealmObject{
    private String option;
    public Option(){}

    public Option(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return option;
    }
}
