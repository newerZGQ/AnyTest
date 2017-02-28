package com.zgq.wokao.action.paper.impl;

import com.zgq.wokao.action.BaseAction;
import com.zgq.wokao.action.paper.IPaperAction;
import com.zgq.wokao.action.paper.IQuestionAction;
import com.zgq.wokao.data.realm.Paper.impl.PaperDaoImpl;
import com.zgq.wokao.model.paper.IExamPaper;
import com.zgq.wokao.model.paper.question.IQuestion;

/**
 * Created by zgq on 2017/2/28.
 */

public class PaperAction extends BaseAction implements IPaperAction,IQuestionAction {

    private PaperDaoImpl paperDao = PaperDaoImpl.getInstance();
    @Override
    public void star(final IExamPaper paper) {
        paperDao.star(paper);
    }

    @Override
    public void unStar(final IExamPaper paper) {
        paperDao.unStar(paper);
    }

    @Override
    public void setTitle(IExamPaper paper, String title) {

    }

    @Override
    public void addToSchedule(final IExamPaper paper) {
        paperDao.addToSchedule(paper);
    }

    @Override
    public void removeFromSchedule(final IExamPaper paper) {
        paperDao.removeFromSchedule(paper);
    }

    @Override
    public void openSchedule(final IExamPaper paper) {
        paperDao.openSchedule(paper);
    }

    @Override
    public void closeSchedule(final IExamPaper paper) {
        paperDao.closeSchedule(paper);
    }

    @Override
    public void setDailyCount(final IExamPaper paper, final int count) {
        paperDao.setDailyCount(paper,count);
    }

    @Override
    public void updateDailyRecord(final IExamPaper paper) {
        paperDao.updateDailyRecord(paper);
    }

    @Override
    public void addExamPaper() {

    }

    @Override
    public void deleteExamPaper() {

    }

    @Override
    public void star(final IQuestion question) {
        paperDao.star(question);
    }

    @Override
    public void unStar(final IQuestion question) {
        paperDao.unStar(question);
    }

    @Override
    public void updateQuestionRecord(final IQuestion question, final boolean isCorrect) {
        paperDao.updateQuestionRecord(question,isCorrect);
    }
}
