package com.zgq.wokao.entity.paper.question.info;

import com.zgq.wokao.entity.CascadeDeleteable;

import io.realm.RealmObject;
import lombok.Data;

/**
 * Created by zgq on 2017/2/27.
 */

@Data
public class QuestionInfo extends RealmObject implements CascadeDeleteable {

    private String id;
    private String type;
    private int qstId;
    private boolean isStared;

    @Override
    public void cascadeDelete() {
        deleteFromRealm();
    }
}
