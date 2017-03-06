package com.zgq.wokao.data.realm.Paper;

import com.zgq.wokao.model.schedule.DailyRecord;
import com.zgq.wokao.model.schedule.Schedule;

/**
 * Created by zgq on 2017/3/7.
 */

public interface IScheduleDao {
    public void addRecord(Schedule schedule,DailyRecord dailyRecord);
}
