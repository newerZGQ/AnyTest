package com.zgq.wokao.action.paper;

import com.zgq.wokao.model.paper.question.IQuestion;

/**
 * Created by zgq on 2017/2/28.
 */

public interface IQuestionInfoAction extends IQuestionAction {
    public void star(IQuestion question);
    public void unStar(IQuestion question);
}
