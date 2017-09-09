package com.zgq.wokao.data.realm.Paper;

import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.paper.question.IQuestion;

/**
 * Created by zgq on 2017/2/28.
 */

public interface IQuestionDao extends IQuestionInfoDao,IQuestionRecordDao {

    /**
     * 更新question，只能更新question的body，answer, options
     * @param questionId
     * @param question
     */
    public void updateQuestion(String questionId,IQuestion question);

    /**
     * 查询question
     * @param questionId
     * @return
     */
    public IQuestion queryQuestionById(String questionId, QuestionType type);
}
