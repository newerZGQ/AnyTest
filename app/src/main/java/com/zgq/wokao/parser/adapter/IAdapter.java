package com.zgq.wokao.parser.adapter;

import com.zgq.wokao.model.paper.QuestionType;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/2/19.
 */

public interface IAdapter <T>{
    public QuestionType getType();
    public ArrayList<T> parse(String resource);
}
