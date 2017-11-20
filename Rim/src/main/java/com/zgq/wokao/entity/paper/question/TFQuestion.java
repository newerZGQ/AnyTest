package com.zgq.wokao.entity.paper.question;

import com.zgq.wokao.entity.CascadeDeleteable;
import com.zgq.wokao.entity.paper.question.answer.Answer;
import com.zgq.wokao.entity.paper.question.body.QuestionBody;
import com.zgq.wokao.entity.paper.question.info.QuestionInfo;
import com.zgq.wokao.entity.paper.question.record.QuestionRecord;

import io.realm.RealmObject;

/**
 * Created by zgq on 16-6-18.
 */
public class TFQuestion extends RealmObject implements CascadeDeleteable {
    private QuestionBody body;
    private Answer answer;
    private QuestionInfo info;
    private QuestionRecord record;

    @Override
    public void cascadeDelete() {
        body.cascadeDelete();
        info.cascadeDelete();
        answer.cascadeDelete();
        record.cascadeDelete();
        deleteFromRealm();
    }
}
