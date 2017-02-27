package com.zgq.wokao.model.schedule;

import com.zgq.wokao.Util.DateUtil;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by zgq on 2017/2/25.
 */

public class Schedule extends RealmObject implements ISchedule{
    //是否开启学习计划
    private boolean isOpened;
    private int dailyCount;
    private RealmList<Record> records = new RealmList<>();

    public boolean isOpened() {
        return isOpened;
    }

    public void close() {
        this.isOpened = false;
    }

    public RealmList<Record> getRecords() {
        return records;
    }

    public void setRecords(RealmList<Record> records) {
        this.records = records;
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
        Record record = new Record.Builder().date(DateUtil.getFormatData("yyyy-MM-dd"))
                .isCompleted(false)
                .studyCount(this.dailyCount)
                .studyNumber(0)
                .build();
        records.add(record);
    }

    @Override
    public Record getcurrentRecord() {
        if (!lastRecordIsCurrent()){
            addRecord();
        }
        return records.get(records.size()-1);
    }

    @Override
    public void recordPlus1() {
        Record record = getcurrentRecord();
        record.addStudyNumber();
    }
    //判断最后一次记录是不是今天的
    private boolean lastRecordIsCurrent(){
        if (records.size() == 0) return false;
        Record last = records.get(records.size()-1);
        String currentData = DateUtil.getYYYY_MM_DD();
        if (last.getDate().equals(currentData)){
            return true;
        }
        return false;
    }


}
