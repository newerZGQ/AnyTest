package com.zgq.wokaofree.model.paper.question.answer;

import com.zgq.wokaofree.model.CascadeDeleteable;

import io.realm.RealmObject;

/**
 * Created by zgq on 2017/2/27.
 */

public class Answer extends RealmObject implements IAnswer, CascadeDeleteable {
    private String content;

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean hasAnswer() {
        if (content == null || content.equals("")) {
            return false;
        }
        return true;
    }

    @Override
    public void cascadeDelete() {
        deleteFromRealm();
    }
}
