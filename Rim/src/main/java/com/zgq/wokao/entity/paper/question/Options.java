package com.zgq.wokao.entity.paper.question;

import com.zgq.wokao.entity.CascadeDeleteable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Options extends RealmObject implements CascadeDeleteable {
    @PrimaryKey
    private String id;
    private RealmList<Option> optionList;

    @Override
    public void cascadeDelete() {
        for (Option option : optionList) {
            option.cascadeDelete();
        }
        deleteFromRealm();
    }
}
