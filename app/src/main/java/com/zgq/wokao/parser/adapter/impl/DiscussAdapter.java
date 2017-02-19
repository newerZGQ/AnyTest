package com.zgq.wokao.parser.adapter.impl;

import com.zgq.wokao.model.paper.DiscussQuestion;
import com.zgq.wokao.model.paper.Question;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.parser.adapter.BaseAdapter;
import com.zgq.wokao.parser.adapter.IDiscussAdapter;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/2/19.
 */

public class DiscussAdapter extends BaseAdapter implements IDiscussAdapter {

    private QuestionType type = QuestionType.fillin;

    @Override
    public QuestionType getType() {
        return type;
    }

    @Override
    public ArrayList<DiscussQuestion> parse(String resource) {
        return null;
    }
}
