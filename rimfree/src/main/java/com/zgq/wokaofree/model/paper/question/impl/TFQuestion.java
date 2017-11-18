package com.zgq.wokaofree.model.paper.question.impl;

import com.zgq.wokaofree.model.CascadeDeleteable;
import com.zgq.wokaofree.model.paper.QuestionType;
import com.zgq.wokaofree.model.paper.question.IQuestion;
import com.zgq.wokaofree.model.paper.question.answer.Answer;
import com.zgq.wokaofree.model.paper.question.body.QuestionBody;
import com.zgq.wokaofree.model.paper.question.info.QuestionInfo;
import com.zgq.wokaofree.model.paper.question.option.Options;
import com.zgq.wokaofree.model.paper.question.record.QuestionRecord;

import io.realm.RealmObject;

/**
 * Created by zgq on 16-6-18.
 */
public class TFQuestion extends RealmObject implements IQuestion, CascadeDeleteable {
    private QuestionBody body;
    private Answer answer;
    private QuestionInfo info;
    private QuestionRecord record;

    public TFQuestion() {
    }

    public TFQuestion(Builder builder) {
        this.body = builder.body;
        this.answer = builder.answer;
        this.info = builder.info;
        this.record = builder.record;
        if (this.body == null) {
            this.body = new QuestionBody();
        }
        if (this.answer == null) {
            this.answer = new Answer();
        }
        if (this.info == null) {
            this.info = new QuestionInfo(QuestionType.TF);
        }
        if (this.record == null) {
            this.record = new QuestionRecord();
        }
    }

    @Override
    public Options getOptions() {
        return null;
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

    @Override
    public String toString() {
        return info.getQstId() + " " + info.getType() + " " + body.getContent() + " " + answer.getContent();
    }

    @Override
    public void cascadeDelete() {
        body.cascadeDelete();
        info.cascadeDelete();
        answer.cascadeDelete();
        record.cascadeDelete();
        deleteFromRealm();
    }

    public static class Builder {
        private com.zgq.wokaofree.model.paper.question.body.QuestionBody body;
        private Answer answer;
        private QuestionInfo info;
        private QuestionRecord record;

        public Builder body(QuestionBody body) {
            this.body = body;
            return this;
        }

        public Builder answer(Answer answer) {
            this.answer = answer;
            return this;
        }

        public Builder info(QuestionInfo info) {
            this.info = info;
            return this;
        }

        public Builder record(QuestionRecord record) {
            this.record = record;
            return this;
        }

        public TFQuestion build() {
            return new TFQuestion(this);
        }
    }
}