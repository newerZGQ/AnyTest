package com.zgq.wokao.data.realm.Paper;

import com.zgq.wokao.model.paper.question.IQuestion;

/**
 * Created by zgq on 2017/2/28.
 */

public interface IQuestionInfoDao {
    public void star(IQuestion question);

    public void unStar(IQuestion question);
}
