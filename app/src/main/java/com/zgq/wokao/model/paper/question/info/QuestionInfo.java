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
    private int studyCount;
    private int correctCount;
    private float accuracy;

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

    public int getStudyCount() {
        return studyCount;
    }

    public void setStudyCount(int studyCount) {
        this.studyCount = studyCount;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    @Override
    public void cascadeDelete() {
        type.cascadeDelete();
        deleteFromRealm();
    }
}
