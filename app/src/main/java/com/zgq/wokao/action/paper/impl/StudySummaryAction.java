package com.zgq.wokao.action.paper.impl;

import android.support.design.widget.TabLayout;
import android.util.Log;

import com.zgq.wokao.action.paper.IStudySummaryAction;
import com.zgq.wokao.data.realm.Paper.impl.StudySummaryDaoImpl;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.total.StudySummary;

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
        if ((type == QuestionType.TF || type == QuestionType.SINGLECHOOSE ||
                type == QuestionType.MUTTICHOOSE) && correct) {
            studySummaryDao.updateSummary(studySummaryDao.getStudySummary(), true);
        }else {
            studySummaryDao.updateSummary(studySummaryDao.getStudySummary(), false);
        }
    }

    @Override
    public StudySummary getStudySummary() {
        return studySummaryDao.getStudySummary();
    }
}
