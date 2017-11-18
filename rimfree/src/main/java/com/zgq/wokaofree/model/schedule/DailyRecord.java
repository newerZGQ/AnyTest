package com.zgq.wokaofree.model.schedule;

import com.zgq.wokaofree.model.CascadeDeleteable;

import io.realm.RealmObject;

/**
 * Created by zgq on 2017/2/25.
 */
public class DailyRecord extends RealmObject implements CascadeDeleteable {
    private String date;
    private int studyNumber;
    private int studyCount;
    private boolean isCompleted;

    public DailyRecord() {
    }

    public DailyRecord(Builder builder) {
        this.date = builder.date;
        this.studyNumber = builder.studyNumber;
        this.studyCount = builder.studyCount;
        this.isCompleted = builder.isCompleted;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStudyNumber() {
        return studyNumber;
    }

    public void setStudyNumber(int studyNumber) {
        this.studyNumber = studyNumber;
    }

    public int getStudyCount() {
        return studyCount;
    }

    public void setStudyCount(int studyCount) {
        this.studyCount = studyCount;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void addStudyNumber() {
        studyNumber++;
    }

    @Override
    public void cascadeDelete() {

    }

    public static class Builder {
        private String date;
        private int studyNumber;
        private int studyCount;
        private boolean isCompleted;

        public Builder date(String date) {
            this.date = date;
            return this;
        }

        public Builder studyNumber(int studyNumber) {
            this.studyNumber = studyNumber;
            return this;
        }

        public Builder studyCount(int studyCount) {
            this.studyCount = studyCount;
            return this;
        }

        public Builder isCompleted(boolean isCompleted) {
            this.isCompleted = isCompleted;
            return this;
        }

        public DailyRecord build() {
            return new DailyRecord(this);
        }

    }
}
