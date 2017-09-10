package com.zgq.wokao.parser;

import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.paper.question.IQuestion;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/2/19.
 */

public interface IQuestionParser {

    void setAdapter(QuestionType type);

    ArrayList<IQuestion> parse(PaperParser.Topic resource);

}
