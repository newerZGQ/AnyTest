package com.zgq.wokao.model.viewdate;

import com.zgq.wokao.model.paper.IExamPaper;

/**
 * Created by zgq on 2017/3/5.
 */

public class ScheduleData {
    private String paperId;
    private String accuracy;
    private String paperTitle;

    private String addTime;
    private int countToday;
    private int countEveryday;
    private int countAllQuestions;

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

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

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
    public static class Formator{
        public static ScheduleData format(IExamPaper paper){
            ScheduleData data = new ScheduleData();
            data.setPaperTitle(paper.getPaperInfo().getTitle());
            data.setCountToday(paper.getPaperInfo().getSchedule().getcurrentRecord().getStudyNumber());
            data.setCountEveryday(paper.getPaperInfo().getSchedule().getDailyCount());
            data.setCountAllQuestions(34);
            data.setPaperId(paper.getPaperInfo().getId());
            return data;
        }

    }
}
