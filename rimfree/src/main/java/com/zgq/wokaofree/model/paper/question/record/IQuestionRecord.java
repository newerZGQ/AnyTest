package com.zgq.wokaofree.model.paper.question.record;

/**
 * Created by zgq on 2017/2/27.
 */

public interface IQuestionRecord {

    /**
     * 更新某一题的答题记录
     *
     * @param isCorrect 表示这道题是否答对
     */
    void updateRecord(boolean isCorrect);

    float getAccuracy();

    void setAccuracy(float accuracy);
}
