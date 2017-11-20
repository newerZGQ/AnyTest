package com.zgq.wokao.entity.paper.question;

import com.zgq.wokao.entity.CascadeDeleteable;

import io.realm.RealmList;
import io.realm.RealmObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by zgq on 2017/2/27.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
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
