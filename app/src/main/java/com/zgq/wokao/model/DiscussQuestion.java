package com.zgq.wokao.model;

import io.realm.RealmObject;

/**
 * Created by zgq on 16-6-18.
 */
public class DiscussQuestion extends RealmObject implements Question  {
    private String body;
    private String answer;
    private int id;
    private String type;
    private boolean isStared;
    private boolean isStudied;

    public DiscussQuestion() {
    }

    public DiscussQuestion(int id, String type,String body, String answer) {
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
    public boolean isStudied() {
        return isStudied;
    }

    @Override
    public void setStudied(boolean studied) {
        this.isStudied = studied;
    }
    @Override
    public String toString() {
        return id+" "+type+" "+body+" "+answer;
    }
}
