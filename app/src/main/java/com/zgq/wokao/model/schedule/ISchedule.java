package com.zgq.wokao.model.schedule;

import com.zgq.wokao.model.paper.QuestionType;

/**
 * Created by zgq on 2017/2/25.
 */

public interface ISchedule {
    public void open();
    public void close();
    public int getDailyCount();
    public void setDailyCount(int count);
    public void addRecord();
    public DailyRecord getcurrentRecord();
    public void recordPlus1();
    public String getLastStudyType();
    public void setLastStudyType(QuestionType lastStudyType);
    public int getLastStudyNum();
    public void setLastStudyNum(int lastStudyNum);
    public void updateStudyInfo(boolean isCorrect);
    public float getAccuracy();
}
