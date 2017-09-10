package com.zgq.wokao.model.paper.question.option;

import com.zgq.wokao.model.CascadeDeleteable;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by zgq on 2017/2/27.
 */

public class Options extends RealmObject implements IOptions, CascadeDeleteable {
    private RealmList<Option> optionList = new RealmList<>();

    @Override
    public boolean hasOptions() {
        return optionList.size() != 0;
    }

    @Override
    public int getOptionsCount() {
        return optionList.size();
    }

    public RealmList<Option> getOptionList() {
        return optionList;
    }

    public void setOptionList(RealmList<Option> optionList) {
        this.optionList = optionList;
    }

    @Override
    public boolean addOption(Option option) {
        return optionList.add(option);
    }

    @Override
    public void cascadeDelete() {
        for (Option option : optionList) {
            option.cascadeDelete();
        }
        deleteFromRealm();
    }
}
