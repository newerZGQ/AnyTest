package com.zgq.wokao.entity.paper.question.answer;

import com.zgq.wokao.entity.CascadeDeleteable;

import io.realm.RealmObject;
import lombok.Data;

/**
 * Created by zgq on 2017/2/27.
 */

@Data
public class Answer extends RealmObject implements CascadeDeleteable {
    private String content;

    @Override
    public void cascadeDelete() {
        deleteFromRealm();
    }
}
