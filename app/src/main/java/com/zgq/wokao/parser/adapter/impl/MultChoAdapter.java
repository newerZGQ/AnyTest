package com.zgq.wokao.parser.adapter.impl;

import com.zgq.wokao.model.paper.DiscussQuestion;
import com.zgq.wokao.model.paper.MultChoQuestion;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.parser.adapter.BaseAdapter;
import com.zgq.wokao.parser.adapter.IMultChoAdapter;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/2/19.
 */

public class MultChoAdapter extends BaseAdapter implements IMultChoAdapter {
    private QuestionType type = QuestionType.fillin;

    @Override
    public QuestionType getType() {
        return type;
    }

    @Override
    public ArrayList<MultChoQuestion> parse(String resource) {
        return null;
    }
}
