package com.zgq.wokaofree.action.paper.impl;

import android.support.design.widget.TabLayout;
import android.util.Log;

import com.zgq.wokaofree.action.paper.IStudySummaryAction;
import com.zgq.wokaofree.data.realm.Paper.impl.StudySummaryDaoImpl;
import com.zgq.wokaofree.model.paper.QuestionType;
import com.zgq.wokaofree.model.total.StudySummary;

/**
 * Created by zhangguoqiang on 2017/4/9.
 */

public class StudySummaryAction implements IStudySummaryAction {
    private static String TAG = StudySummaryAction.class.getSimpleName();

    private StudySummaryDaoImpl studySummaryDao = StudySummaryDaoImpl.getInstance();

    private StudySummaryAction() {
    }

    public static StudySummaryAction getInstance() {
        return InstanceHolder.instance;
    }

    public static class InstanceHolder {
        public static StudySummaryAction instance = new StudySummaryAction();
    }

    @Override
    public void initStudySummary() {
        if (studySummaryDao.getStudySummary() != null){
            return;
        }
        Log.d(TAG, "initStudySummary");
        studySummaryDao.addStudySummary(new StudySummary());
        studySummaryDao.initStudySummary(studySummaryDao.getStudySummary());
    }

    @Override
    public void updateSummary(QuestionType type, boolean correct) {
        studySummaryDao.updateSummary(studySummaryDao.getStudySummary(), correct);
    }

    @Override
    public StudySummary getStudySummary() {
        return studySummaryDao.getStudySummary();
    }
}
