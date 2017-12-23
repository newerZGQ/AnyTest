package com.zgq.wokao.parser;


import com.zgq.wokao.entity.paper.question.IQuestion;
import com.zgq.wokao.entity.paper.question.QuestionType;

import java.util.ArrayList;

public interface IQuestionParser {

    void setAdapter(QuestionType type);

    ArrayList<IQuestion> parse(PaperParser.Topic resource);

}
