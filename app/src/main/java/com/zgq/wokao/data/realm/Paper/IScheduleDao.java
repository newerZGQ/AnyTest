package com.zgq.wokao.data.realm.Paper;

import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.schedule.DailyRecord;
import com.zgq.wokao.model.schedule.Schedule;

/**
 * Created by zgq on 2017/3/7.
 */

public interface IScheduleDao {
    void addRecord(Schedule schedule, DailyRecord dailyRecord);

    void setLastStudyInfo(String paperId, QuestionType questionType, int num);

    QuestionType getLastStudyType(String paperId);

    int getLastStudyNum(String paperId);
}
