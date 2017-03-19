package com.zgq.wokao.model.paper.question.info;

/**
 * Created by zgq on 16-6-19.
 */
public interface IQuestionInfo {
    public int getStudyCount();

    public void setStudyCount(int studyCount);

    public int getCorrectCount() ;

    public void setCorrectCount(int correctCount);

    public float getAccuracy() ;

    public void setAccuracy(float accuracy);

    public boolean isStared();
}