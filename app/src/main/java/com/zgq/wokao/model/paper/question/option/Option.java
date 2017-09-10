package com.zgq.wokao.model.paper.question.option;

import com.zgq.wokao.model.CascadeDeleteable;

import io.realm.RealmObject;

/**
 * Created by zgq on 16-6-18.
 */
public class Option extends RealmObject implements CascadeDeleteable {
    private String option;
    private String tag;

    public Option() {
    }

    public Option(Builder builder) {
        this.option = builder.option;
        this.tag = builder.tag;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return option;
    }

    @Override
    public void cascadeDelete() {
        deleteFromRealm();
    }

    public static class Builder {
        private String option;
        private String tag;

        public Builder option(String option) {
            this.option = option;
            return this;
        }

        public Builder tag(String tag) {
            this.tag = tag;
            return this;
        }

        public Option build() {
            return new Option(this);
        }
    }
}
