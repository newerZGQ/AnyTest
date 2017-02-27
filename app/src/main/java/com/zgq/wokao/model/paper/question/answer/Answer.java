package com.zgq.wokao.model.paper.question.answer;

import io.realm.RealmObject;

/**
 * Created by zgq on 2017/2/27.
 */

public class Answer extends RealmObject implements IAnswer {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean hasAnswer() {
        if (content == null || content.equals("")){
            return false;
        }
        return true;
    }
}
