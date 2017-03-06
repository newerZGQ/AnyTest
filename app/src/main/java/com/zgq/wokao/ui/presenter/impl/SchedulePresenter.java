package com.zgq.wokao.ui.presenter.impl;

import android.util.Log;

import com.zgq.wokao.action.paper.impl.PaperAction;
import com.zgq.wokao.action.parser.ParserAction;
import com.zgq.wokao.model.paper.IExamPaper;
import com.zgq.wokao.model.paper.info.IPaperInfo;
import com.zgq.wokao.model.viewdate.ScheduleData;
import com.zgq.wokao.ui.presenter.ISchedulePresenter;
import com.zgq.wokao.ui.view.IScheduleView;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/3/5.
 */

public class SchedulePresenter implements ISchedulePresenter {
    public static final String TAG = "SchedulePresenter";
    private IScheduleView scheduleView;
    private PaperAction paperAction = PaperAction.getInstance();

    private ArrayList<ScheduleData> scheduleDatas = new ArrayList<>();
    public SchedulePresenter(IScheduleView scheduleView){
        this.scheduleView = scheduleView;
        init();
    }
    private void init(){
        getScheduleData();
    }

    private void getScheduleData(){
        scheduleDatas.clear();
        ArrayList<IExamPaper> papers = (ArrayList) paperAction.getAllPaperInSchdl();
        for (IExamPaper paper: papers){
            scheduleDatas.add(ScheduleData.Formator.format(paper));
        }
    }

    public void onStartBtnClick(){

    }

    @Override
    public void setViewPager() {
        scheduleView.setViewPager(scheduleDatas);
    }

    @Override
    public void notifyDataChanged() {
        getScheduleData();
        setViewPager();
    }
}
