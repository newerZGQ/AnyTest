package com.zgq.wokaofree.data.realm.Paper;

import com.zgq.wokaofree.model.paper.question.IQuestion;

/**
 * Created by zgq on 2017/2/28.
 */

public interface IQuestionInfoDao {
    void star(IQuestion question);

    void unStar(IQuestion question);
}
