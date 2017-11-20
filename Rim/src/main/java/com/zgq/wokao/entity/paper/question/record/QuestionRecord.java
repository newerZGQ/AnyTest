package com.zgq.wokao.entity.paper.question.record;

import com.zgq.wokao.entity.CascadeDeleteable;

import io.realm.RealmObject;
import lombok.Data;

/**
 * Created by zgq on 2017/2/27.
 */

@Data
public class QuestionRecord extends RealmObject implements CascadeDeleteable {
    private int studyNumber;
    private int correctNumber;
    @Override
    public void cascadeDelete() {
        deleteFromRealm();
    }
}
