package com.zgq.wokao.data;

import java.lang.annotation.Annotation;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;
import io.realm.annotations.RealmModule;

/**
 * Created by zgq on 16-6-18.
 */
public class FillInQuestion extends RealmObject implements Question{
    private String body;
    private String answer;
    private int id;
    private String type;
    private boolean isStared;

    public FillInQuestion() {
    }

    public FillInQuestion(int id, String type,String body, String answer) {
        this.body = body;
        this.id = id;
        this.type = type;
        this.answer = answer;
    }

    @Override
    public int getId() {
        return id;
    }
    @Override
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public void setType(String type) {
        this.type = type;
    }
    @Override
    public String getType() {
        return this.type;
    }
    @Override
    public boolean hasBody() {
        return (body!=null)?true:false;
    }
    @Override
    public String getBody() {
        return (hasBody())?body:null;
    }
    @Override
    public void setBody(String questionBody) {
        this.body = questionBody;
    }
    @Override
    public boolean hasAnswer() {
        return (answer!=null)?true:false;
    }
    @Override
    public void setAnswer(String questionAnswer) {
        this.answer = questionAnswer;
    }
    @Override
    public String getAnswer() {
        return (hasAnswer())?answer:null;
    }
    @Override
    public boolean isStared() {
        return isStared;
    }

    @Override
    public void setStared(boolean stared) {
        this.isStared = stared;
    }

    @Override
    public String toString() {
        return id+" "+type+" "+body+" "+answer;
    }

}
