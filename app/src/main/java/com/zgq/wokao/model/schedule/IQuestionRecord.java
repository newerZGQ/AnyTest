package com.zgq.wokao.model.schedule;

/**
 * Created by zgq on 2017/2/27.
 */

public interface IQuestionRecord {

    /**
     * 更新某一题的答题记录
     * @param isCorrect 表示这道题是否答对
     */
    public void updateRecord(boolean isCorrect);
}
