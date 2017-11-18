package com.zgq.wokaofree.action.paper;

import com.zgq.wokaofree.model.paper.question.IQuestion;

/**
 * Created by zgq on 2017/2/28.
 */

public interface IQuestionInfoAction {
    void star(IQuestion question);

    void unStar(IQuestion question);
}
