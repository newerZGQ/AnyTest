package com.zgq.wokao.ui.view;

import com.zgq.wokao.model.viewdate.ScheduleData;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/3/5.
 */

public interface IScheduleView {
    public void onStartBtnClick();
    public void setListener();
    public void setViewPager(ArrayList<ScheduleData> scheduleDatas);
}
