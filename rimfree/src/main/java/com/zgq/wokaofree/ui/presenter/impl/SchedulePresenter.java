package com.zgq.wokaofree.ui.presenter.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import com.zgq.wokaofree.action.paper.impl.PaperAction;
import com.zgq.wokaofree.action.viewdata.ViewDataAction;
import com.zgq.wokaofree.model.paper.IExamPaper;
import com.zgq.wokaofree.model.paper.QuestionType;
import com.zgq.wokaofree.model.viewdate.QstData;
import com.zgq.wokaofree.model.viewdate.ScheduleData;
import com.zgq.wokaofree.ui.presenter.ISchedulePresenter;
import com.zgq.wokaofree.ui.view.IScheduleView;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/3/5.
 */

public class SchedulePresenter implements ISchedulePresenter {
    public static final String TAG = SchedulePresenter.class.getSimpleName();
    private PaperAction paperAction = PaperAction.getInstance();

    private ArrayList<IExamPaper> schedulePapers = new ArrayList<>();
    private ArrayList<ScheduleData> scheduleDatas = new ArrayList<>();
    private ArrayList<ArrayList<QstData>> qstDatasList = new ArrayList<>();

    public SchedulePresenter(IScheduleView scheduleView, Context context) {

    }

    private void getData() {
        schedulePapers.clear();
        schedulePapers = getSchedulePaper();

        scheduleDatas.clear();
        scheduleDatas = getScheduleData(schedulePapers);

        qstDatasList.clear();
        qstDatasList = getQstData(schedulePapers);
    }

    private ArrayList<IExamPaper> getSchedulePaper() {
        return (ArrayList) paperAction.getAllPaperInSchdl();
    }

    private ArrayList<ScheduleData> getScheduleData(ArrayList<IExamPaper> papers) {
        ArrayList<ScheduleData> results = new ArrayList<>();
        for (IExamPaper paper : papers) {
            results.add(ScheduleData.Formator.format(paper));
        }
        return results;
    }

    private ArrayList<ArrayList<QstData>> getQstData(ArrayList<IExamPaper> papers) {
        ArrayList<ArrayList<QstData>> results = new ArrayList<>();
        for (IExamPaper paper : papers) {
            results.add(ViewDataAction.getInstance().getQstData(paper));
        }
        return results;
    }

    @Override
    public
    @NonNull
    ArrayList<ScheduleData> getScheduleDatas() {
        return scheduleDatas;
    }

    @Override
    public void updateDatas() {
        getData();
    }

    @Override
    public ScheduleData getScheduleInfo(int position) {
        if (position > scheduleDatas.size() - 1) return null;
        return scheduleDatas.get(position);
    }

    @Override
    public int getPaperCount() {
        return schedulePapers.size();
    }

    @Override
    public void setDailyCount(int position, int count) {
        String paperId = schedulePapers.get(position).getPaperInfo().getId();
        paperAction.setDailyCount(paperAction.queryById(paperId), count);
        schedulePapers = getSchedulePaper();
        scheduleDatas = getScheduleData(schedulePapers);
    }

    @Override
    public QuestionType getLastStudyType(String paperId) {
        QuestionType type = QuestionType.valueOf(paperAction.queryById(paperId).getPaperInfo().getSchedule().getLastStudyType());
        return type;
    }

    @Override
    public int getLastStudyPos(String paperId) {
        return paperAction.queryById(paperId).getPaperInfo().getSchedule().getLastStudyNum();
    }
}