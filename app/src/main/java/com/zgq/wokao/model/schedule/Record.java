package com.zgq.wokao.model.schedule;

import io.realm.RealmObject;

/**
 * Created by zgq on 2017/2/25.
 */
public class Record extends RealmObject{
    private String date;
    private int studyNumber;
    private int studyCount;
    private boolean isCompleted;

    public Record(Builder builder){
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

    public void addStudyNumber(){
        studyNumber++;
    }

    public static class Builder{
        private String date;
        private int studyNumber;
        private int studyCount;
        private boolean isCompleted;
        public Builder date(String date){
            this.date = date;
            return this;
        }
        public Builder studyNumber(int studyNumber){
            this.studyNumber = studyNumber;
            return this;
        }
        public Builder studyCount(int studyCount){
            this.studyCount = studyCount;
            return this;
        }
        public Builder isCompleted(boolean isCompleted){
            this.isCompleted = isCompleted;
            return this;
        }

        public Record build(){
            return new Record(this);
        }

    }
}
