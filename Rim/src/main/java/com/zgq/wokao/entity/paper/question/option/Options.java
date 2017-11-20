package com.zgq.wokao.entity.paper.question.option;

import com.zgq.wokao.entity.CascadeDeleteable;

import io.realm.RealmList;
import io.realm.RealmObject;
import lombok.Data;

/**
 * Created by zgq on 2017/2/27.
 */

@Data
public class Options extends RealmObject implements CascadeDeleteable {
    private RealmList<Option> optionList;

    @Override
    public void cascadeDelete() {
        for (Option option : optionList) {
            option.cascadeDelete();
        }
        deleteFromRealm();
    }
}
