package com.zgq.wokao.model;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by zgq on 16-6-20.
 */
public class ExamPaperInfo extends RealmObject implements Serializable{
    private String title;
    private String author;
    private String createDate;
    private String lastStudyDate = "1990-10-03 11:11:00";
    private boolean stared = false;
    private boolean parseRight;
    private int studyCount;

    public ExamPaperInfo() {
    }

    public ExamPaperInfo(String title, String author, String createDate, String lastStudyDate) {
        this.title = title;
        this.author = author;
        this.createDate = createDate;
        this.lastStudyDate = lastStudyDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastStudyDate() {
        return lastStudyDate;
    }

    public void setLastStudyDate(String lastStudyDate) {
        this.lastStudyDate = lastStudyDate;
    }

    public boolean isStared() {
        return stared;
    }

    public void setStared(boolean stared) {
        this.stared = stared;
    }

    public boolean isParseRight() {
        return parseRight;
    }

    public void setParseRight(boolean parseRight) {
        this.parseRight = parseRight;
    }

    public int getStudyCount() {
        return studyCount;
    }

    public void setStudyCount(int studyCount) {
        this.studyCount = studyCount;
    }

    @Override
    public String toString() {
        return title + author;
    }
}
