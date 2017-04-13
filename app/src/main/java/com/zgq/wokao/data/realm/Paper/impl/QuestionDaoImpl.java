package com.zgq.wokao.data.realm.Paper.impl;

import com.zgq.wokao.data.realm.BaseRealmProvider;
import com.zgq.wokao.data.realm.Paper.IQuestionDao;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.paper.info.ExamPaperInfo;
import com.zgq.wokao.model.paper.question.IQuestion;
import com.zgq.wokao.model.paper.question.impl.DiscussQuestion;
import com.zgq.wokao.model.paper.question.impl.FillInQuestion;
import com.zgq.wokao.model.paper.question.impl.MultChoQuestion;
import com.zgq.wokao.model.paper.question.impl.SglChoQuestion;
import com.zgq.wokao.model.paper.question.impl.TFQuestion;
import com.zgq.wokao.model.paper.question.info.QuestionInfo;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by zgq on 2017/3/19.
 */

public class QuestionDaoImpl extends BaseRealmProvider<QuestionInfo> implements IQuestionDao{
    Realm realm = Realm.getDefaultInstance();
    private QuestionDaoImpl(){}

    @Override
    public void updateQuestion(String questionId, IQuestion question) {
        IQuestion destQuestion = queryQuestionById(questionId,question.getInfo().getType());
        if (destQuestion == null){
            return;
        }
        realm.beginTransaction();
        switch (question.getInfo().getType().getIndex()){
            case QuestionType.fillin_index:
                FillInQuestion fillin = (FillInQuestion) destQuestion;
                fillin.getBody().setContent(question.getBody().getContent());
                fillin.getAnswer().setContent(question.getAnswer().getContent());
            case QuestionType.tf_index:
                TFQuestion tfQuestion = (TFQuestion) destQuestion;
                tfQuestion.getBody().setContent(question.getBody().getContent());
                tfQuestion.getAnswer().setContent(question.getAnswer().getContent());
            case QuestionType.sglc_index:
                SglChoQuestion sglChoQuestion = (SglChoQuestion) destQuestion;
                sglChoQuestion.getBody().setContent(question.getBody().getContent());
                sglChoQuestion.getAnswer().setContent(question.getAnswer().getContent());
                sglChoQuestion.getOptions().setOptionList(question.getOptions().getOptionList());
            case QuestionType.mtlc_index:
                MultChoQuestion multChoQuestion = (MultChoQuestion) destQuestion;
                multChoQuestion.getBody().setContent(question.getBody().getContent());
                multChoQuestion.getAnswer().setContent(question.getAnswer().getContent());
                multChoQuestion.getOptions().setOptionList(question.getOptions().getOptionList());
            case QuestionType.disc_index:
                DiscussQuestion discussQuestion = (DiscussQuestion) destQuestion;
                discussQuestion.getBody().setContent(question.getBody().getContent());
                discussQuestion.getAnswer().setContent(question.getAnswer().getContent());
                discussQuestion.getOptions().setOptionList(question.getOptions().getOptionList());
        }
        realm.commitTransaction();

    }

    @Override
    public IQuestion queryQuestionById(String questionId, QuestionType type) {
        switch (type.getIndex()){
            case QuestionType.fillin_index:
                FillInQuestion fillin = realm
                        .where(FillInQuestion.class)
                        .equalTo("info.id", questionId)
                        .findFirst();
                return fillin;
            case QuestionType.tf_index:
                TFQuestion tfQuestion = realm
                        .where(TFQuestion.class)
                        .equalTo("info.id",questionId)
                        .findFirst();
                return tfQuestion;
            case QuestionType.sglc_index:
                SglChoQuestion sglChoQuestion = realm
                        .where(SglChoQuestion.class)
                        .equalTo("info.id",questionId)
                        .findFirst();
                return sglChoQuestion;
            case QuestionType.mtlc_index:
                MultChoQuestion multChoQuestion = realm
                        .where(MultChoQuestion.class)
                        .equalTo("info.id",questionId)
                        .findFirst();
                return multChoQuestion;
            case QuestionType.disc_index:
                DiscussQuestion discussQuestion = realm
                        .where(DiscussQuestion.class)
                        .equalTo("info.id",questionId)
                        .findFirst();
                return discussQuestion;

        }
        return null;

    }

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
