package com.zgq.wokaofree.data.realm.Paper;

import com.zgq.wokaofree.model.paper.QuestionType;
import com.zgq.wokaofree.model.paper.question.IQuestion;

/**
 * Created by zgq on 2017/2/28.
 */

public interface IQuestionDao extends IQuestionInfoDao, IQuestionRecordDao {

    /**
     * 更新question，只能更新question的body，answer, options
     *
     * @param questionId
     * @param question
     */
    void updateQuestion(String questionId, IQuestion question);

    /**
     * 查询question
     *
     * @param questionId
     * @return
     */
    IQuestion queryQuestionById(String questionId, QuestionType type);
}
