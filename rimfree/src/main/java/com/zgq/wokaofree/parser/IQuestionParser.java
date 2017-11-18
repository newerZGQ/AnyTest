package com.zgq.wokaofree.parser;

import com.zgq.wokaofree.model.paper.QuestionType;
import com.zgq.wokaofree.model.paper.question.IQuestion;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/2/19.
 */

public interface IQuestionParser {

    void setAdapter(QuestionType type);

    ArrayList<IQuestion> parse(PaperParser.Topic resource);

}
