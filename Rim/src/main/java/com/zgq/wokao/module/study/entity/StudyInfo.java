package com.zgq.wokao.module.study.entity;

import com.zgq.wokao.entity.paper.question.Answer;
import com.zgq.wokao.entity.paper.question.IQuestion;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StudyInfo<T extends IQuestion> {

    private List<T> questions;
    private HashMap<String, Answer> myAnswer = new HashMap<>();

    public StudyInfo(){}

    public void setQuestions(List<T> questions){
        this.questions = questions;
    }

    public List<T> getQuestions(){
        return questions;
    }

    public boolean hasAnswered(String questionId){
        return myAnswer.containsKey(questionId);
    }

    public void saveMyAnswer(@Nonnull String questionId, @Nonnull Answer answer) {
        myAnswer.put(questionId, answer);
    }

    @Nullable
    public Answer getMyAnswer(@Nonnull String questionId) {
        return myAnswer.get(questionId);
    }
}
