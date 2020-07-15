package com.zgq.wokao.parser.adapter;

import com.zgq.wokao.entity.paper.question.QuestionType;

import java.util.ArrayList;

public interface IAdapter<T> {
    QuestionType getType();

    ArrayList<T> parse(String resource);
}
