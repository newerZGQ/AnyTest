package com.zgq.wokao.ui.presenter;

import com.zgq.wokao.model.viewdate.ScheduleData;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/3/5.
 */

public interface ISchedulePresenter {
    public void setViewPager();
    public void notifyDataChanged();
    public void scheduleInfoChangeData(int position);
    public int checkSchedulesSize();
}
