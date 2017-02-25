package com.zgq.wokao.model.paper;

import com.zgq.wokao.model.schedule.Schedule;
import com.zgq.wokao.model.search.Searchable;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by zgq on 16-6-20.
 */
public class ExamPaperInfo extends RealmObject implements Serializable ,Searchable {
    private String id;
    private String title;
    private String author;
    private String createDate;
    private String lastStudyDate = "1990-10-03 11:11:00";
    private boolean stared = false;
    private boolean parseRight;
    private int studyCount;

    //是否加入日程学习
    private boolean isInSchedule;
    private Schedule schedule;



    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public ExamPaperInfo(String id, String title, String author, String createDate, String lastStudyDate, Schedule schedule) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.createDate = createDate;
        this.lastStudyDate = lastStudyDate;
        this.schedule = schedule;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isInSchedule() {
        return isInSchedule;
    }

    public void setInSchedule(boolean inSchedule) {
        isInSchedule = inSchedule;
    }

    public ExamPaperInfo() {
    }

    public Schedule getSchedule() {
        return schedule;
    }

    @Override
    public String toString() {
        return title + author;
    }
}
