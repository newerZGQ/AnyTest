package com.zgq.wokao.ui.view;

import com.zgq.wokao.model.viewdate.QstData;
import com.zgq.wokao.model.viewdate.ScheduleData;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/3/5.
 */

public interface IScheduleView {
    public void setViewPager(ArrayList<ScheduleData> scheduleDatas);
    public void notifyDataChanged();
    public void scheduleInfoChangeData(ScheduleData data);
    public void onEmptyPapers();
    public void onNoneEmptyPapers();
}
