package com.zgq.wokao.model.schedule;

import io.realm.RealmObject;

/**
 * Created by zgq on 2017/2/27.
 */

public class QuestionRecord extends RealmObject implements IQuestionRecord{
    private int studyNumber;
    private int correctNumber;
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

//    public static class Builder{
//        private int studyNumber;
//        private int correctNumber;
//
//        public Builder studyNumber(int studyNumber){
//            this.studyNumber = studyNumber;
//            return this;
//        }
//        public Builder correctNumber(int correctNumber){
//            this.correctNumber = correctNumber;
//            return this;
//        }
//        public QuestionRecord build(){
//            return new QuestionRecord(this);
//        }
//    }

}
