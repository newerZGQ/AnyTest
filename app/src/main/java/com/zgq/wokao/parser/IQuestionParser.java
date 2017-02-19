package com.zgq.wokao.parser;

import com.zgq.wokao.model.paper.Question;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.parser.adapter.IAdapter;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/2/19.
 */

public interface IQuestionParser {

    public void setAdapter(QuestionType type);
    public ArrayList<Question> parse(String resource);

}
