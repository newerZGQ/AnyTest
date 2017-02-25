package com.zgq.wokao.model.schedule;

import io.realm.RealmObject;

/**
 * Created by zgq on 2017/2/25.
 */

public class Record extends RealmObject{
    private String date;
    private int studyNumber;
    private boolean isCompleted;

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

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
