package com.zgq.wokaofree.model.paper.question.body;

import com.zgq.wokaofree.model.CascadeDeleteable;

import io.realm.RealmObject;

/**
 * Created by zgq on 2017/2/27.
 */

public class QuestionBody extends RealmObject implements IQuestionBody, CascadeDeleteable {
    private String content;

    @Override
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void cascadeDelete() {
        deleteFromRealm();
    }
}
