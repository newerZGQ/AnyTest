package com.zgq.wokao.parser.adapter.impl;

import android.graphics.Path;

import com.zgq.wokao.Util.ListUtil;
import com.zgq.wokao.Util.StringUtil;
import com.zgq.wokao.model.paper.DiscussQuestion;
import com.zgq.wokao.model.paper.Option;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.paper.SglChoQuestion;
import com.zgq.wokao.model.paper.TFQuestion;
import com.zgq.wokao.parser.adapter.BaseAdapter;
import com.zgq.wokao.parser.adapter.ISglChoAdapter;
import com.zgq.wokao.parser.context.QuestionContext;
import com.zgq.wokao.parser.context.item.QuestionItem;
import com.zgq.wokao.parser.context.item.QuestionItemType;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/2/19.
 */

public class SglChoAdapter extends BaseAdapter implements ISglChoAdapter {
    private QuestionType type = QuestionType.sglc;
    ArrayList<String> content = new ArrayList<>();
    QuestionContext context = new QuestionContext();
    private ArrayList<SglChoQuestion> results = new ArrayList<>();

    public SglChoAdapter() {
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
    public ArrayList<SglChoQuestion> parse(String resource) {
        String[] strings = resource.split("\n");
        content = (ArrayList<String>) ListUtil.array2list(strings);
        parseRes(content);
        return results;
    }

    private ArrayList<SglChoQuestion> parseRes(ArrayList<String> content) {
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
//                System.out.println("---->>is number"+tmp);
                //把上一次循环的题干和答案提取出来
                bodyString = builder.toString();
                if (!bodyString.equals("")){
                    SglChoQuestion question = parseSingle(number, bodyString);
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
//                System.out.println("---->>not number"+tmp);
                builder.append("\n" + tmp);
            }
        }

        SglChoQuestion question = parseSingle(number, builder.toString());
        if (question != null) {
            results.add(question);
        }
        return results;
    }

    private SglChoQuestion parseSingle(int number, String questionRes) {
//        System.out.println("---->>single" + questionRes);
        SglChoQuestion question = new SglChoQuestion();
        question.setId(number);
        inContext(QuestionItemType.number);
        String[] resArray = trimNum(questionRes).split("\n");
        StringBuilder builder = new StringBuilder();
        int headBack = 0;
        for (String tmp : resArray) {
            tmp = tmp.trim();
            int head = tmp.substring(0,1).toUpperCase().charAt(0);
//            System.out.println("---->>"+head);
            if (head == 65 && !startWithWord(tmp)){
                String body = builder.toString();
                builder.delete(0,builder.length());
                question.setBody(body);
                context.inContext(QuestionItemType.body);
                builder.append(getOptionContent(tmp));
                continue;
            }
            if (head > 65 && head <= 77 && !startWithWord(tmp)){
                String optionContent = builder.toString();
                String tag = StringUtil.char2String((char)(head-1));
                headBack = head;
                question.addOption(new Option(optionContent,tag));
                context.inContext(QuestionItemType.option);
                builder.delete(0,builder.length());
                builder.append(getOptionContent(tmp));
                continue;
            }
            if (tmp.startsWith("答案")) {
                String optionContent = builder.toString();
                String tag = StringUtil.char2String((char)headBack);
                question.addOption(new Option(optionContent,tag));
                builder.delete(0,builder.length());
                builder.append(tmp.substring(2).trim());
                continue;
            }
            builder.append(tmp);
        }
        String answer = builder.toString();
        if (answer.startsWith(":") || answer.startsWith("：")) {
            question.setAnswer(answer.substring(1).trim());
        } else {
            question.setAnswer(answer);
        }
        inContext(QuestionItemType.answer);
        return question;
    }

    private String getOptionContent(String s){
        s = s.trim().substring(1).trim();
        if (s.startsWith(":")||s.startsWith("：")){
            s = s.trim().substring(1).trim();
        }
        return s;
    }
    private void inContext(QuestionItemType type) {
        context.inContext(new QuestionItem(type));
    }
}