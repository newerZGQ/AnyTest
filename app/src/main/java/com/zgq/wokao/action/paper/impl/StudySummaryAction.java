package com.zgq.wokao.action.paper.impl;

import com.zgq.wokao.action.paper.IStudySummaryAction;
import com.zgq.wokao.data.realm.Paper.impl.StudySummaryDaoImpl;
import com.zgq.wokao.model.total.StudySummary;

/**
 * Created by zhangguoqiang on 2017/4/9.
 */

public class StudySummaryAction implements IStudySummaryAction {
    private StudySummaryDaoImpl studySummaryDao = StudySummaryDaoImpl.getInstance();

    private StudySummaryAction(){}

    public static StudySummaryAction getInstance(){
        return InstanceHolder.instance;
    }
    public static class InstanceHolder{
        public static StudySummaryAction instance = new StudySummaryAction();
    }
    @Override
    public void addStudySummary(StudySummary studySummary) {

        studySummaryDao.addStudySummary(studySummary);
    }

    @Override
    public void updateSummary(StudySummary studySummary, boolean correct) {
        studySummaryDao.updateSummary(studySummary,correct);
    }

    @Override
    public StudySummary getStudySummary() {
        return studySummaryDao.getStudySummary();
    }
}
