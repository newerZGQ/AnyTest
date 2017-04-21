package com.zgq.wokao.ui.presenter.impl;

import android.content.Context;
import android.util.Log;

import com.zgq.wokao.Util.LogUtil;
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
import java.util.List;

/**
 * Created by zgq on 2017/3/5.
 */

public class SchedulePresenter implements ISchedulePresenter {
    public static final String TAG = SchedulePresenter.class.getSimpleName();
    private IScheduleView scheduleView;
    private Context context;
    private PaperAction paperAction = PaperAction.getInstance();

    private ArrayList<IExamPaper> schedulePapers = new ArrayList<>();
    private ArrayList<ScheduleData> scheduleDatas = new ArrayList<>();
    private ArrayList<ArrayList<QstData>> qstDatasList = new ArrayList<>();

    public SchedulePresenter(IScheduleView scheduleView, Context context){
        this.scheduleView = scheduleView;
        this.context = context;
        getData();
    }

    public void getData(){
        schedulePapers.clear();
        schedulePapers = getSchedulePaper();

        scheduleDatas.clear();
        scheduleDatas = getScheduleData(schedulePapers);

        qstDatasList.clear();
        qstDatasList = getQstData(schedulePapers);
    }

    private ArrayList<IExamPaper> getSchedulePaper(){
        return (ArrayList) paperAction.getAllPaperInSchdl();
    }
    private ArrayList<ScheduleData> getScheduleData(ArrayList<IExamPaper> papers){
        ArrayList<ScheduleData> results = new ArrayList<>();
        for (IExamPaper paper: papers){
            results.add(ScheduleData.Formator.format(paper));
        }
        return results;
    }
    private ArrayList<ArrayList<QstData>> getQstData(ArrayList<IExamPaper> papers){
        ArrayList<ArrayList<QstData>> results = new ArrayList<>();
        for (IExamPaper paper: papers){
            results.add(ViewDataAction.getInstance().getQstData(paper));
        }
        return results;
    }

    public List<QstData> getQstDataByPosition(int position){
        return qstDatasList.get(position);
    }

    @Override
    public void setViewPager() {
        scheduleView.setViewPager(scheduleDatas,qstDatasList);
    }

    @Override
    public void notifyDataChanged() {
        getData();
        setViewPager();
    }

    @Override
    public void scheduleInfoChangeData(int position) {
        scheduleView.scheduleInfoChangeData(scheduleDatas.get(position));
    }

    @Override
    public int checkSchedulesSize() {
        if (schedulePapers.size() == 0){
            scheduleView.onEmptyPapers();
        }else{
            scheduleView.onNoneEmptyPapers();
        }
        return schedulePapers.size();
    }
}
