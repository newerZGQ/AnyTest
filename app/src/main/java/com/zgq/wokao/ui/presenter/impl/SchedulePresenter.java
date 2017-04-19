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

    private void getData(){
        Log.d(LogUtil.PREFIX,TAG + " getData in");
        getSchedulePaper();
        getScheduleData();
        getQstData();
        Log.d(LogUtil.PREFIX,TAG + " getData out");
    }

    private void getSchedulePaper(){
        schedulePapers.clear();
        schedulePapers = (ArrayList) paperAction.getAllPaperInSchdl();
    }
    private void getScheduleData(){
        scheduleDatas.clear();
        for (IExamPaper paper: schedulePapers){
            scheduleDatas.add(ScheduleData.Formator.format(paper));
        }
    }
    private void getQstData(){
        qstDatasList.clear();
        for (IExamPaper paper: schedulePapers){
            qstDatasList.add(ViewDataAction.getInstance().getQstData(paper));
        }
//        Log.d(LogUtil.PREFIX,TAG+qstDatasList.get(0).get(0).getStudyNum());
//        Log.d(LogUtil.PREFIX,TAG+qstDatasList.get(0).get(1).getStudyNum());
//        Log.d(LogUtil.PREFIX,TAG+qstDatasList.get(0).get(2).getStudyNum());
//        Log.d(LogUtil.PREFIX,TAG+qstDatasList.get(0).get(3).getStudyNum());
//        Log.d(LogUtil.PREFIX,TAG+qstDatasList.get(0).get(4).getStudyNum());
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
}
