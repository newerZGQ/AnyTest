package com.zgq.wokao.model.paper;

import io.realm.RealmObject;

/**
 * Created by zgq on 16-6-18.
 */
public class Option extends RealmObject{
    private String option;
    private String tag;
    public Option(){}

    public Option(String option,String tag) {
        this.option = option;
        this.tag = tag;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return option;
    }
}
