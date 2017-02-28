package com.zgq.wokao.action.paper.impl;

import com.zgq.wokao.action.BaseAction;
import com.zgq.wokao.action.paper.IPaperAction;
import com.zgq.wokao.action.paper.IPaperInfoAction;
import com.zgq.wokao.action.paper.IPaperSchdlAction;
import com.zgq.wokao.action.paper.IQuestionAction;
import com.zgq.wokao.action.paper.IQuestionInfoAction;
import com.zgq.wokao.action.paper.IQuestionRecordAction;
import com.zgq.wokao.model.paper.IExamPaper;
import com.zgq.wokao.model.paper.question.IQuestion;

import io.realm.Realm;

/**
 * Created by zgq on 2017/2/28.
 */

public class PaperAction extends BaseAction implements IPaperAction,IPaperInfoAction
        ,IPaperSchdlAction,IQuestionAction,
        IQuestionInfoAction,IQuestionRecordAction{
    @Override
    public void star(final IExamPaper paper) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                paper.getPaperInfo().setStared(true);
            }
        });
    }

    @Override
    public void unStar(final IExamPaper paper) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                paper.getPaperInfo().setStared(false);
            }
        });
    }

    @Override
    public void setTitle(IExamPaper paper, String title) {

    }

    @Override
    public void addToSchedule(final IExamPaper paper) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                paper.getPaperInfo().setInSchedule(true);
            }
        });
    }

    @Override
    public void removeFromSchedule(final IExamPaper paper) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                paper.getPaperInfo().setInSchedule(false);
            }
        });
    }

    @Override
    public void openSchedule(final IExamPaper paper) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                paper.getPaperInfo().getSchedule().open();
            }
        });
    }

    @Override
    public void closeSchedule(final IExamPaper paper) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                paper.getPaperInfo().getSchedule().close();
            }
        });
    }

    @Override
    public void setDailyCount(final IExamPaper paper, final int count) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                paper.getPaperInfo().getSchedule().setDailyCount(count);
            }
        });
    }

    @Override
    public void updateDailyRecord(final IExamPaper paper) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                paper.getPaperInfo().getSchedule().recordPlus1();
            }
        });
    }

    @Override
    public void addExamPaper() {

    }

    @Override
    public void deleteExamPaper() {

    }

    @Override
    public void star(final IQuestion question) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                question.getInfo().setStared(true);
            }
        });
    }

    @Override
    public void unStar(final IQuestion question) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                question.getInfo().setStared(false);
            }
        });
    }

    @Override
    public void updateQuestionRecord(final IQuestion question, final boolean isCorrect) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                question.getRecord().updateRecord(isCorrect);
            }
        });
    }
}
