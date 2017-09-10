package com.zgq.wokao.model.paper.question.option;

import io.realm.RealmList;

/**
 * Created by zgq on 16-6-18.
 */
public interface IOptions {
    public abstract boolean hasOptions();

    public abstract int getOptionsCount();

    public abstract RealmList<Option> getOptionList();

    public abstract void setOptionList(RealmList<Option> optionList);

    public abstract boolean addOption(Option option);
}
