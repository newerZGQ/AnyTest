package com.zgq.wokao.action.paper;

import com.zgq.wokao.model.paper.IExamPaper;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.paper.question.IQuestion;
import com.zgq.wokao.model.paper.question.info.QuestionInfo;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/2/28.
 */

public interface IQuestionAction extends IQuestionInfoAction, IQuestionRecordAction{
    public ArrayList<IQuestion> getQuestins(IExamPaper paper, QuestionType type);
}
