package com.zgq.wokao.ui.presenter.impl;

import android.util.Log;

import com.zgq.wokao.action.paper.impl.PaperAction;
import com.zgq.wokao.action.parser.ParserAction;
import com.zgq.wokao.action.viewdata.ViewDataAction;
import com.zgq.wokao.model.paper.IExamPaper;
import com.zgq.wokao.model.paper.info.IPaperInfo;
import com.zgq.wokao.model.viewdate.QstData;
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
    private ArrayList<ArrayList<QstData>> qstDatasList = new ArrayList<>();

    public SchedulePresenter(IScheduleView scheduleView){
        this.scheduleView = scheduleView;
        init();
    }

    private void init(){
        getScheduleData();
        getQstData();
    }

    private void getScheduleData(){
        scheduleDatas.clear();
        ArrayList<IExamPaper> papers = (ArrayList) paperAction.getAllPaperInSchdl();
        for (IExamPaper paper: papers){
            scheduleDatas.add(ScheduleData.Formator.format(paper));
        }
    }

    private void getQstData(){
        qstDatasList.clear();
        ArrayList<IExamPaper> papers = (ArrayList) paperAction.getAllPaperInSchdl();
        for (IExamPaper paper: papers){
            qstDatasList.add(ViewDataAction.getInstance().getQstData(paper));
        }
    }

    public void onStartBtnClick(){

    }

    @Override
    public void setViewPager() {
        scheduleView.setViewPager(scheduleDatas,qstDatasList);
    }

    @Override
    public void notifyDataChanged() {
        getScheduleData();
        getQstData();
        setViewPager();
    }
}
