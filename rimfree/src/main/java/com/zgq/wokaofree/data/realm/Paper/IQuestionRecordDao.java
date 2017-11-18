package com.zgq.wokaofree.data.realm.Paper;

import com.zgq.wokaofree.model.paper.question.IQuestion;

/**
 * Created by zgq on 2017/2/28.
 */

public interface IQuestionRecordDao {
    void updateQuestionRecord(IQuestion question, boolean isCorrect);
}
