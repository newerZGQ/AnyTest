package com.zgq.wokao.model.schedule;

/**
 * Created by zgq on 2017/2/25.
 */

public interface ISchedule {
    public int getDailyCount();
    public void setDailyCount(int count);
    public void addRecord(Record record);
}
