package com.zgq.wokao.data;

import java.util.ArrayList;

import io.realm.RealmList;

/**
 * Created by zgq on 16-6-18.
 */
public interface QuestionOptions {
    public abstract boolean hasOptions();
    public abstract int getOptionsCount();
    public abstract RealmList<Option> getOptions();
    public abstract void setOptions(RealmList<Option> options);
    public abstract boolean addOption(Option option);
}
