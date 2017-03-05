package com.zgq.wokao.model.viewdate;

/**
 * Created by zgq on 2017/3/5.
 */

public class ScheduleData {
    private String accuracy;
    private String paperTitle;
    private int countToday;
    private int countEveryday;
    private int countAllQuestions;

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public int getCountToday() {
        return countToday;
    }

    public void setCountToday(int countToday) {
        this.countToday = countToday;
    }

    public int getCountEveryday() {
        return countEveryday;
    }

    public void setCountEveryday(int countEveryday) {
        this.countEveryday = countEveryday;
    }

    public int getCountAllQuestions() {
        return countAllQuestions;
    }

    public void setCountAllQuestions(int countAllQuestions) {
        this.countAllQuestions = countAllQuestions;
    }
    public static class Formator{

    }
}
