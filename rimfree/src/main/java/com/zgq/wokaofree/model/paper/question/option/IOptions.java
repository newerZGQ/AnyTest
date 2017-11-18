package com.zgq.wokaofree.model.paper.question.option;

import io.realm.RealmList;

/**
 * Created by zgq on 16-6-18.
 */
public interface IOptions {
    boolean hasOptions();

    int getOptionsCount();

    RealmList<Option> getOptionList();

    void setOptionList(RealmList<Option> optionList);

    boolean addOption(Option option);
}
