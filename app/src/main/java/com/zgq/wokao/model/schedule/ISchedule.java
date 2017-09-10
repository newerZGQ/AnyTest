package com.zgq.wokao.model.schedule;

import com.zgq.wokao.model.paper.QuestionType;

/**
 * Created by zgq on 2017/2/25.
 */

public interface ISchedule {
    void open();

    void close();

    int getDailyCount();

    void setDailyCount(int count);

    void addRecord();

    DailyRecord getcurrentRecord();

    void recordPlus1();

    String getLastStudyType();

    void setLastStudyType(QuestionType lastStudyType);

    int getLastStudyNum();

    void setLastStudyNum(int lastStudyNum);

    void updateStudyInfo(boolean isCorrect);

    float getAccuracy();
}
