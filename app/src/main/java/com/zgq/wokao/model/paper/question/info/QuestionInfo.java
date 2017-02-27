package com.zgq.wokao.model.paper.question.info;

import com.zgq.wokao.model.paper.QuestionType;

import io.realm.RealmObject;

/**
 * Created by zgq on 2017/2/27.
 */

public class QuestionInfo extends RealmObject implements IQuestionInfo {

    private QuestionType type;
    private int id;
    private boolean isStared;

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isStared() {
        return isStared;
    }

    public void setStared(boolean stared) {
        isStared = stared;
    }
}
