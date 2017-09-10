package com.zgq.wokao.action.paper;

import com.zgq.wokao.model.paper.IExamPaper;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.paper.question.IQuestion;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/2/28.
 */

public interface IQuestionAction extends IQuestionInfoAction, IQuestionRecordAction {
    ArrayList<IQuestion> getQuestins(IExamPaper paper, QuestionType type);

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

    void updateQuestionRecord(IQuestion question, boolean isCorrect);
}
