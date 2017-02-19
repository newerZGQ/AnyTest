package com.zgq.wokao.parser.adapter.impl;

import com.zgq.wokao.model.paper.DiscussQuestion;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.paper.SglChoQuestion;
import com.zgq.wokao.parser.adapter.BaseAdapter;
import com.zgq.wokao.parser.adapter.ISglChoAdapter;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/2/19.
 */

public class SglChoAdapter extends BaseAdapter implements ISglChoAdapter {
    private QuestionType type = QuestionType.fillin;

    @Override
    public QuestionType getType() {
        return type;
    }

    @Override
    public ArrayList<SglChoQuestion> parse(String resource) {
        return null;
    }
}
