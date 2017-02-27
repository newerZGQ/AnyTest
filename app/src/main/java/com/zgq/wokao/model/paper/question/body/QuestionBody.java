package com.zgq.wokao.model.paper.question.body;

import io.realm.RealmObject;

/**
 * Created by zgq on 2017/2/27.
 */

public class QuestionBody extends RealmObject implements IQuestionBody {
    private String content;

    @Override
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
