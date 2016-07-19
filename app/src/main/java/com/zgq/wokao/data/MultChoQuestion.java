package com.zgq.wokao.data;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by zgq on 16-6-18.
 */
public class MultChoQuestion extends RealmObject implements Question, QuestionOptions{
    private int id;
    private String type;
    private String body;
    private String answer;
    private boolean isStared;
    private boolean isStudied;
    private RealmList<Option> options = new RealmList<>();

    public MultChoQuestion() {
    }

    public MultChoQuestion(int id, String type,String body, String answer,RealmList<Option> options) {
        this.body = body;
        this.id = id;
        this.type = type;
        this.answer = answer;
        this.options = options;
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
    public int getOptionsCount() {
        if (options == null) return 0;
        return options.size();
    }

    @Override
    public RealmList<Option> getOptions() {
        return options;
    }

    @Override
    public boolean hasOptions() {
        if (options == null || options.size() == 0){
            return false;
        }
        return true;
    }

    @Override
    public void setOptions(RealmList<Option> options) {
        this.options = options;
    }

    @Override
    public boolean addOption(Option option) {
        if (option == null) return false;
        options.add(option);
        return true;
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
