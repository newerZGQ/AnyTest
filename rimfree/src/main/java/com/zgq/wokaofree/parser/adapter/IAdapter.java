package com.zgq.wokaofree.parser.adapter;

import com.zgq.wokaofree.model.paper.QuestionType;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/2/19.
 */

public interface IAdapter<T> {
    QuestionType getType();

    ArrayList<T> parse(String resource);
}
