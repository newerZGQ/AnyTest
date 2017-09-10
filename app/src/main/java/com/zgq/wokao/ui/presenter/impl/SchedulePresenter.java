package com.zgq.wokao.ui.presenter.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import com.zgq.wokao.action.paper.impl.PaperAction;
import com.zgq.wokao.action.viewdata.ViewDataAction;
import com.zgq.wokao.model.paper.IExamPaper;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.viewdate.QstData;
import com.zgq.wokao.model.viewdate.ScheduleData;
import com.zgq.wokao.ui.presenter.ISchedulePresenter;
import com.zgq.wokao.ui.view.IScheduleView;

import java.util.ArrayList;

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

    @Override
    public @NonNull ArrayList<ScheduleData> getScheduleDatas(){
        return scheduleDatas;
    }

    @Override
    public void notifyDataChanged() {
        getData();
    }

    @Override
    public void scheduleInfoChangeData(int position) {
        if (position > scheduleDatas.size()-1) return;
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

    @Override
    public void setDailyCount(int position, int count) {
        String paperId = schedulePapers.get(position).getPaperInfo().getId();
        paperAction.setDailyCount(paperAction.queryById(paperId),count);
        schedulePapers = getSchedulePaper();
        scheduleDatas = getScheduleData(schedulePapers);
    }

    @Override
    public QuestionType getLastStudyType(String paperId) {
        QuestionType  type = QuestionType.valueOf(paperAction.queryById(paperId).getPaperInfo().getSchedule().getLastStudyType());
        return type;
    }

    @Override
    public int getLastStudyPos(String paperId) {
        return paperAction.queryById(paperId).getPaperInfo().getSchedule().getLastStudyNum();
    }
}
