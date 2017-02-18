package com.zgq.wokao.parser.adapter;

import com.zgq.wokao.model.paper.Question;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by zgq on 2017/2/19.
 */

public interface IAdapter {
    public ArrayList<Question> parse(String resource);
}
