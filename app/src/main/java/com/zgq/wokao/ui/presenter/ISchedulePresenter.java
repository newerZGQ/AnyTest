package com.zgq.wokao.ui.presenter;

import com.zgq.wokao.model.viewdate.ScheduleData;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/3/5.
 */

public interface ISchedulePresenter {
    ArrayList<ScheduleData> getScheduleDatas();
    void notifyDataChanged();
    void scheduleInfoChangeData(int position);
    int checkSchedulesSize();
    void setDailyCount(int position, int count);
}
