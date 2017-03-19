package com.zgq.wokao.data.realm.Paper.impl;

import com.zgq.wokao.data.realm.BaseRealmProvider;
import com.zgq.wokao.data.realm.Paper.IQuestionDao;
import com.zgq.wokao.model.paper.question.IQuestion;
import com.zgq.wokao.model.paper.question.info.QuestionInfo;

import io.realm.Realm;

/**
 * Created by zgq on 2017/3/19.
 */

public class QuestionDaoImpl extends BaseRealmProvider<QuestionInfo> implements IQuestionDao{
    Realm realm = Realm.getDefaultInstance();
    private QuestionDaoImpl(){}
    public static class InstanceHolder{
        public static QuestionDaoImpl instance = new QuestionDaoImpl();
    }

    public static QuestionDaoImpl getInstance(){
        return InstanceHolder.instance;
    }
    @Override
    public synchronized void star(final IQuestion question) {
        realm.beginTransaction();
        question.getInfo().setStared(true);
        realm.commitTransaction();
    }

    @Override
    public synchronized void unStar(final IQuestion question) {
        realm.beginTransaction();
        question.getInfo().setStared(false);
        realm.commitTransaction();
    }

    @Override
    public synchronized void updateQuestionRecord(final IQuestion question, final boolean isCorrect) {
        realm.beginTransaction();
        question.getRecord().updateRecord(isCorrect);
        realm.commitTransaction();
    }
}
