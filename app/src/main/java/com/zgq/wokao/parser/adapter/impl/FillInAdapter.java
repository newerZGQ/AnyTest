package com.zgq.wokao.parser.adapter.impl;

import android.util.Log;

import com.zgq.wokao.Util.ListUtil;
import com.zgq.wokao.Util.UUIDUtil;
import com.zgq.wokao.model.paper.question.impl.FillInQuestion;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.parser.adapter.BaseAdapter;
import com.zgq.wokao.parser.adapter.IFillInAdapter;
import com.zgq.wokao.parser.context.QuestionContext;
import com.zgq.wokao.parser.context.item.QuestionItem;
import com.zgq.wokao.parser.context.item.QuestionItemType;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/2/19.
 */

public class FillInAdapter extends BaseAdapter implements IFillInAdapter {
    private QuestionType type = QuestionType.fillin;
    ArrayList<String> content = new ArrayList<>();
    QuestionContext context = new QuestionContext();
    private ArrayList<FillInQuestion> results = new ArrayList<>();

    public FillInAdapter() {
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
    public ArrayList<FillInQuestion> parse(String resource) {
        String[] strings = resource.split("\n");
        content = (ArrayList<String>) ListUtil.array2list(strings);
        parseRes(content);
        return results;
    }

    private ArrayList<FillInQuestion> parseRes(ArrayList<String> content) {
        //题号
        int number = 1;
        String bodyString = "";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < content.size(); i++) {
            String tmp = content.get(i);
            //如果这一行为空，则继续，不做任何操作
            if (tmp.equals("")) {
                continue;
            }
            //如果这一行是题目开始的地方
            if (isQstNumber(tmp)) {
//                System.out.println("---->>is number"+tmp);
                //把上一次循环的题干和答案提取出来
                bodyString = builder.toString();
                if (bodyString.equals("")){

                }else{
                    FillInQuestion question = parseSingle(number, bodyString);
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
                builder.append("\n" + tmp);
            }
        }

        FillInQuestion question = parseSingle(number, builder.toString());
        if (question != null) {
            results.add(question);
        }
        return results;
    }

    private FillInQuestion parseSingle(int number, String questionRes) {
        FillInQuestion question = new FillInQuestion.Builder().build();
        if (question == null){
            Log.d("---->>","question null");
        }
        if (question.getInfo() == null){
            Log.d("---->>","questioninfo null");
        }
        question.getInfo().setQstId(number);
        question.getInfo().setId(UUIDUtil.getID());
        inContext(QuestionItemType.number);
        String[] resArray = trimNum(questionRes).split("\n");
        StringBuilder builder = new StringBuilder();
        for (String tmp : resArray) {
            tmp = tmp.trim();
            if (tmp.startsWith("答案")) {
                question.getBody().setContent(builder.toString());
                inContext(QuestionItemType.body);
                String answerTmp = tmp.substring(2).trim();
                if (answerTmp.startsWith(":") || answerTmp.startsWith("：")) {
                    question.getAnswer().setContent(answerTmp.substring(1).trim());
                } else {
                    question.getAnswer().setContent(answerTmp);
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
