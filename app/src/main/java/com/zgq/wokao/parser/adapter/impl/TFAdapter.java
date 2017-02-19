package com.zgq.wokao.parser.adapter.impl;

import com.zgq.wokao.model.paper.DiscussQuestion;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.paper.TFQuestion;
import com.zgq.wokao.parser.adapter.BaseAdapter;
import com.zgq.wokao.parser.adapter.ITFAdapter;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/2/19.
 */

public class TFAdapter extends BaseAdapter implements ITFAdapter {
    private QuestionType type = QuestionType.fillin;

    @Override
    public QuestionType getType() {
        return type;
    }

    @Override
    public ArrayList<TFQuestion> parse(String resource) {
        return null;
    }
}
