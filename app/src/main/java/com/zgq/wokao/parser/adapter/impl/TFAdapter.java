package com.zgq.wokao.parser.adapter.impl;

import com.zgq.wokao.Util.ListUtil;
import com.zgq.wokao.model.paper.FillInQuestion;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.paper.TFQuestion;
import com.zgq.wokao.parser.adapter.BaseAdapter;
import com.zgq.wokao.parser.adapter.ITFAdapter;
import com.zgq.wokao.parser.context.QuestionContext;
import com.zgq.wokao.parser.context.item.QuestionItem;
import com.zgq.wokao.parser.context.item.QuestionItemType;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/2/19.
 */

public class TFAdapter extends BaseAdapter implements ITFAdapter {
    private QuestionType type = QuestionType.tf;
    ArrayList<String> content = new ArrayList<>();
    QuestionContext context = new QuestionContext();
    private ArrayList<TFQuestion> results = new ArrayList<>();

    public TFAdapter() {
        initParam();
    }

    private void initParam() {
        context.init(5);
    }

    @Override
    public QuestionType getType() {
        return type;
    }

    @Override
    public ArrayList<TFQuestion> parse(String resource) {
        String[] strings = resource.split("\n");
        content = (ArrayList<String>) ListUtil.array2list(strings);
        parseRes(content);
        return results;
    }

    private ArrayList<TFQuestion> parseRes(ArrayList<String> content) {
        //题号
        int number = 1;
        String bodyString = "";
        StringBuilder builder = new StringBuilder();
//        System.out.println("---->>content"+content.size());
        for (int i = 0; i < content.size(); i++) {
            String tmp = content.get(i);
            //如果这一行为空，则继续，不做任何操作
            if (tmp.equals("")) {
                continue;
            }
            //如果这一行是题目开始的地方
            if (isQstNumber(tmp)) {
                System.out.println("---->>is number"+tmp);
                //把上一次循环的题干和答案提取出来
                bodyString = builder.toString();
                if (bodyString.equals("")){

                }else{
                    TFQuestion question = parseSingle(number, bodyString);
                    if (question != null) {
                        results.add(question);
                        number++;
                    }
                }
                if (builder.length() != 0) {
                    builder.delete(0, builder.length());
                }
                String contentTmp = trimNum(tmp);
                builder.append(contentTmp);
            } else {
                System.out.println("---->>not number"+tmp);
                builder.append("\n" + tmp);
            }
        }

        TFQuestion question = parseSingle(number, builder.toString());
        if (question != null) {
            results.add(question);
        }
        return results;
    }

    private TFQuestion parseSingle(int number, String questionRes) {
        System.out.println("---->>single" + questionRes);
        TFQuestion question = new TFQuestion();
        question.setId(number);
        inContext(QuestionItemType.number);
        String[] resArray = trimNum(questionRes).split("\n");
        StringBuilder builder = new StringBuilder();
        for (String tmp : resArray) {
            tmp = tmp.trim();
            if (tmp.startsWith("答案")) {
                question.setBody(builder.toString());
                inContext(QuestionItemType.body);
                String answerTmp = tmp.substring(2).trim();
                if (answerTmp.startsWith(":") || answerTmp.startsWith("：")) {
                    question.setAnswer(answerTmp.substring(1).trim());
                } else {
                    question.setAnswer(answerTmp);
                }
                inContext(QuestionItemType.answer);
                continue;
            }
            builder.append(tmp);
        }
        return question;
    }

    private void inContext(QuestionItemType type) {
        context.inContext(new QuestionItem(type));
    }
}