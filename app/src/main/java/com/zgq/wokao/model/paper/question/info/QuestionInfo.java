package com.zgq.wokao.model.paper.question.info;

import com.zgq.wokao.model.CascadeDeleteable;
import com.zgq.wokao.model.paper.QuestionType;

import io.realm.RealmObject;

/**
 * Created by zgq on 2017/2/27.
 */

public class QuestionInfo extends RealmObject implements IQuestionInfo ,CascadeDeleteable{

    private String id;
    private QuestionType type;
    private int qstId;
    private boolean isStared;

    public QuestionInfo(){}
    public QuestionInfo(QuestionType type){
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public int getQstId() {
        return qstId;
    }

    public void setQstId(int qstId) {
        this.qstId = qstId;
    }

    public boolean isStared() {
        return isStared;
    }

    public void setStared(boolean stared) {
        isStared = stared;
    }

    @Override
    public void cascadeDelete() {
        type.cascadeDelete();
        deleteFromRealm();
    }
}
