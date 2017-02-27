package com.zgq.wokao.parser;

import com.zgq.wokao.model.paper.question.IQuestion;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.parser.adapter.IAdapter;
import com.zgq.wokao.parser.adapter.impl.DiscussAdapter;
import com.zgq.wokao.parser.adapter.impl.FillInAdapter;
import com.zgq.wokao.parser.adapter.impl.MultChoAdapter;
import com.zgq.wokao.parser.adapter.impl.SglChoAdapter;
import com.zgq.wokao.parser.adapter.impl.TFAdapter;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/2/19.
 */

public class QuestionParser extends BaseParser implements IQuestionParser{

    private QuestionType type;
    private IAdapter adapter;
    private ArrayList<IQuestion> results = new ArrayList<>();


    @Override
    public void setAdapter(QuestionType type) {
        this.type = type;
        switch (type.getIndex()){
            case QuestionType.fillin_index:
                adapter = new FillInAdapter();
                break;
            case QuestionType.tf_index:
                adapter = new TFAdapter();
                break;
            case QuestionType.sglc_index:
                adapter = new SglChoAdapter();
                break;
            case QuestionType.mtlc_index:
                adapter = new MultChoAdapter();
                break;
            case QuestionType.disc_index:
                adapter = new DiscussAdapter();
                break;
        }
    }

    @Override
    public ArrayList<IQuestion> parse(PaperParser.Topic resource) {
        results =  adapter.parse(resource.getContent());
        return  results;
    }
}
