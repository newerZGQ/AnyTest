package com.zgq.wokao.model.paper.question.impl;

import com.zgq.wokao.model.paper.question.IQuestion;
import com.zgq.wokao.model.paper.question.answer.Answer;
import com.zgq.wokao.model.paper.question.body.QuestionBody;
import com.zgq.wokao.model.paper.question.info.QuestionInfo;
import com.zgq.wokao.model.paper.question.option.Options;
import com.zgq.wokao.model.paper.question.record.QuestionRecord;

import io.realm.RealmObject;

/**
 * Created by zgq on 16-6-18.
 */
public class MultChoQuestion extends RealmObject implements IQuestion {
    private QuestionBody body;
    private Answer answer;
    private QuestionInfo info;
    private QuestionRecord record;
    private Options options;

    public MultChoQuestion(Builder builder) {
        this.body = builder.body;
        this.answer = builder.answer;
        this.info = builder.info;
        this.record = builder.record;
        this.options = builder.options;
    }

    public QuestionBody getBody() {
        return body;
    }

    public void setBody(QuestionBody body) {
        this.body = body;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public QuestionInfo getInfo() {
        return info;
    }

    public void setInfo(QuestionInfo info) {
        this.info = info;
    }

    public QuestionRecord getRecord() {
        return record;
    }

    public void setRecord(QuestionRecord record) {
        this.record = record;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return info.getId()+" "+info.getType()+" "+body.getContent()+" "+answer.getContent();
    }

    public static class Builder{
        private QuestionBody body;
        private Answer answer;
        private QuestionInfo info;
        private QuestionRecord record;
        private Options options;
        public Builder body(QuestionBody body){
            this.body = body;
            return this;
        }
        public Builder answer(Answer answer){
            this.answer = answer;
            return this;
        }
        public Builder info(QuestionInfo info){
            this.info = info;
            return this;
        }
        public Builder record(QuestionRecord record){
            this.record = record;
            return this;
        }
        public Builder options(Options options){
            this.options = options;
            return this;
        }

        public MultChoQuestion build(){
            return new MultChoQuestion(this);
        }
    }
}
