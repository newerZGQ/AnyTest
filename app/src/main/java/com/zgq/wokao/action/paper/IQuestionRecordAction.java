package com.zgq.wokao.action.paper;

import com.zgq.wokao.model.paper.question.IQuestion;

/**
 * Created by zgq on 2017/2/28.
 */

public interface IQuestionRecordAction extends IQuestionAction{
    public void updateQuestionRecord(IQuestion question,boolean isCorrect);
}
