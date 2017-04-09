package com.zgq.wokao.model.total;

import io.realm.RealmObject;

/**
 * Created by zhangguoqiang on 2017/4/9.
 */

public class TotalDailyCount extends RealmObject{
    private int dailyCount;
    private String date;
    public TotalDailyCount(){}
    public TotalDailyCount(int dailyCount) {
        this.dailyCount = dailyCount;
    }

    public int getDailyCount() {
        return dailyCount;
    }

    public void setDailyCount(int dailyCount) {
        this.dailyCount = dailyCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
