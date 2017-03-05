package com.zgq.wokao.ui.presenter.impl;

import com.zgq.wokao.action.paper.impl.PaperAction;
import com.zgq.wokao.action.parser.ParserAction;
import com.zgq.wokao.model.viewdate.ScheduleData;
import com.zgq.wokao.ui.presenter.ISchedulePresenter;
import com.zgq.wokao.ui.view.IScheduleView;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/3/5.
 */

public class SchedulePresenter implements ISchedulePresenter {
    private IScheduleView scheduleView;
    private PaperAction paperAction = PaperAction.getInstance();
    public SchedulePresenter(IScheduleView scheduleView){
        this.scheduleView = scheduleView;
    }
    public void onStartBtnClick(){

    }

    @Override
    public void setViewPager(ArrayList<ScheduleData> scheduleDatas) {
        scheduleView.setViewPager(scheduleDatas);
    }
}
