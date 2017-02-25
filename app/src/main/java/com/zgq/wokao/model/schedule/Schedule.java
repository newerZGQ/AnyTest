package com.zgq.wokao.model.schedule;

import com.zgq.wokao.Util.DateUtil;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by zgq on 2017/2/25.
 */

public class Schedule extends RealmObject implements ISchedule{
    //是否开启学习计划
    private boolean isOpened;
    private int dailyCount;
    private RealmList<DailyRecord> dailyRecords = new RealmList<>();

    public boolean isOpened() {
        return isOpened;
    }

    public void close() {
        this.isOpened = false;
    }

    public RealmList<DailyRecord> getDailyRecords() {
        return dailyRecords;
    }

    public void setDailyRecords(RealmList<DailyRecord> dailyRecords) {
        this.dailyRecords = dailyRecords;
    }

    @Override
    public void open() {
        this.isOpened = true;
    }

    @Override
    public int getDailyCount() {
        return dailyCount;
    }

    @Override
    public void setDailyCount(int count) {
        this.dailyCount = count;
    }

    @Override
    public void addRecord() {
        if (lastRecordIsCurrent()){
            return;
        }
        DailyRecord dailyRecord = new DailyRecord.Builder().date(DateUtil.getFormatData("yyyy-MM-dd"))
                .isCompleted(false)
                .studyCount(this.dailyCount)
                .studyNumber(0)
                .build();
        dailyRecords.add(dailyRecord);
    }

    @Override
    public DailyRecord getcurrentRecord() {
        if (!lastRecordIsCurrent()){
            addRecord();
        }
        return dailyRecords.get(dailyRecords.size()-1);
    }

    @Override
    public void recordPlus1() {
        DailyRecord dailyRecord = getcurrentRecord();
        dailyRecord.addStudyNumber();
    }
    //判断最后一次记录是不是今天的
    private boolean lastRecordIsCurrent(){
        if (dailyRecords.size() == 0) return false;
        DailyRecord last = dailyRecords.get(dailyRecords.size()-1);
        String currentData = DateUtil.getYYYY_MM_DD();
        if (last.getDate().equals(currentData)){
            return true;
        }
        return false;
    }


}