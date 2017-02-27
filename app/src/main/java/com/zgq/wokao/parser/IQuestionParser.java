package com.zgq.wokao.parser;

import com.zgq.wokao.model.paper.question.IQuestion;
import com.zgq.wokao.model.paper.QuestionType;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/2/19.
 */

public interface IQuestionParser {

    public void setAdapter(QuestionType type);
    public ArrayList<IQuestion> parse(PaperParser.Topic resource);

}
