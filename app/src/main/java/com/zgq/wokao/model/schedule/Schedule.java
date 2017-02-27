package com.zgq.wokao.model.schedule;

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

    public void setOpened(boolean opened) {
        isOpened = opened;
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

    }

    @Override
    public Record getcurrentRecord() {
        return null;
    }

    @Override
    public void recordPlus1() {

    }


}
