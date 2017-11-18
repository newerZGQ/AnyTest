package com.zgq.wokaofree.parser;

import com.zgq.wokaofree.model.paper.QuestionType;
import com.zgq.wokaofree.model.paper.question.IQuestion;
import com.zgq.wokaofree.parser.adapter.IAdapter;
import com.zgq.wokaofree.parser.adapter.impl.DiscussAdapter;
import com.zgq.wokaofree.parser.adapter.impl.FillInAdapter;
import com.zgq.wokaofree.parser.adapter.impl.MultChoAdapter;
import com.zgq.wokaofree.parser.adapter.impl.SglChoAdapter;
import com.zgq.wokaofree.parser.adapter.impl.TFAdapter;

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
