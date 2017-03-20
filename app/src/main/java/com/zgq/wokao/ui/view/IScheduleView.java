package com.zgq.wokao.ui.view;

import com.zgq.wokao.model.viewdate.QstData;
import com.zgq.wokao.model.viewdate.ScheduleData;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/3/5.
 */

public interface IScheduleView {
    public void setListener();
    public void setViewPager(ArrayList<ScheduleData> scheduleDatas, ArrayList<ArrayList<QstData>> qstDataLists);
    public void notifyDataChanged();
    public void showDetail();
    public void hideDetail();
    public void scheduleInfoChangeData(ScheduleData data);
    public void changeViewPagerStatus(boolean showFullView);
}
