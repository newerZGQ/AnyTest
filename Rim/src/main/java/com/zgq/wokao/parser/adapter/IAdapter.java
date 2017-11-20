package com.zgq.wokao.parser.adapter;

import com.zgq.wokao.entity.paper.question.QuestionType;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/2/19.
 */

public interface IAdapter<T> {
    QuestionType getType();

    ArrayList<T> parse(String resource);
}
