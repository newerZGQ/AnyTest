package com.zgq.wokao.model.paper.question.record;

import io.realm.RealmObject;

/**
 * Created by zgq on 2017/2/27.
 */

public class QuestionRecord extends RealmObject implements IQuestionRecord{
    private int studyNumber;
    private int correctNumber;
    //表示这道题是否学习过，一旦试卷所有试题都学习完，则会重置为false;
    private boolean isStudied;
    @Override
    public void updateRecord(boolean isCorrect){
        studyNumber++;
        if (isCorrect) correctNumber++;
    }
    public int getStudyNumber() {
        return studyNumber;
    }

    public void setStudyNumber(int studyNumber) {
        this.studyNumber = studyNumber;
    }

    public int getCorrectNumber() {
        return correctNumber;
    }

    public void setCorrectNumber(int correctNumber) {
        this.correctNumber = correctNumber;
    }

    public boolean isStudied() {
        return isStudied;
    }

    public void setStudied(boolean studied) {
        isStudied = studied;
    }
}