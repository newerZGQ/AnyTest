package com.zgq.wokaofree.data.realm.Paper;

import com.zgq.wokaofree.model.paper.QuestionType;
import com.zgq.wokaofree.model.schedule.DailyRecord;
import com.zgq.wokaofree.model.schedule.Schedule;

/**
 * Created by zgq on 2017/3/7.
 */

public interface IScheduleDao {
    void addRecord(Schedule schedule, DailyRecord dailyRecord);

    void setLastStudyInfo(String paperId, QuestionType questionType, int num);

    QuestionType getLastStudyType(String paperId);

    int getLastStudyNum(String paperId);
}
