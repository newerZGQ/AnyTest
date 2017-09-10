package com.zgq.wokao.parser;

import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.paper.question.IQuestion;
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

public class QuestionParser extends BaseParser implements IQuestionParser {

    private QuestionType type;
    private IAdapter adapter;
    private ArrayList<IQuestion> results = new ArrayList<>();


    @Override
    public void setAdapter(QuestionType type) {
        switch (type) {
            case FILLIN:
                adapter = new FillInAdapter();
                break;
            case TF:
                adapter = new TFAdapter();
                break;
            case SINGLECHOOSE:
                adapter = new SglChoAdapter();
                break;
            case MUTTICHOOSE:
                adapter = new MultChoAdapter();
                break;
            case DISCUSS:
                adapter = new DiscussAdapter();
                break;
            default:
                break;
        }
    }

    @Override
    public ArrayList<IQuestion> parse(PaperParser.Topic resource) {
        results = adapter.parse(resource.getContent());
        return results;
    }
}
