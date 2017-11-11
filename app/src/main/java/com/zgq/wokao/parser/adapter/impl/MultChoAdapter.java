package com.zgq.wokao.parser.adapter.impl;

import com.zgq.wokao.Util.ListUtil;
import com.zgq.wokao.Util.StringUtil;
import com.zgq.wokao.Util.UUIDUtil;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.paper.question.impl.MultChoQuestion;
import com.zgq.wokao.model.paper.question.option.Option;
import com.zgq.wokao.parser.adapter.BaseAdapter;
import com.zgq.wokao.parser.adapter.IMultChoAdapter;
import com.zgq.wokao.parser.context.QuestionContext;
import com.zgq.wokao.parser.context.item.QuestionItem;
import com.zgq.wokao.parser.context.item.QuestionItemType;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/2/19.
 */

public class MultChoAdapter extends BaseAdapter implements IMultChoAdapter {
    private QuestionType type = QuestionType.MUTTICHOOSE;
    ArrayList<String> content = new ArrayList<>();
    QuestionContext context = new QuestionContext();
    private ArrayList<MultChoQuestion> results = new ArrayList<>();

    public MultChoAdapter() {
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
    public ArrayList<MultChoQuestion> parse(String resource) {
        String[] strings = resource.split("\n");
        content = (ArrayList<String>) ListUtil.array2list(strings);
        parseRes(content);
        return results;
    }

    private ArrayList<MultChoQuestion> parseRes(ArrayList<String> content) {
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
                //把上一次循环的题干和答案提取出来
                bodyString = builder.toString();
                if (!bodyString.equals("")) {
                    MultChoQuestion question = parseSingle(number, bodyString);
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

        MultChoQuestion question = parseSingle(number, builder.toString());
        if (question != null) {
            results.add(question);
        }
        return results;
    }

    private MultChoQuestion parseSingle(int number, String questionRes) {
        MultChoQuestion question = new MultChoQuestion.Builder().build();
        question.getInfo().setQstId(number);
        question.getInfo().setId(UUIDUtil.getID());
        inContext(QuestionItemType.number);
        String[] resArray = trimNum(questionRes).split("\n");
        StringBuilder builder = new StringBuilder();
        int headBack = 0;
        for (String tmp : resArray) {
            tmp = tmp.trim();
            int head = tmp.toUpperCase().charAt(1);
            char headTag = tmp.toUpperCase().charAt(0);
            if (head == 'A' && !startWithWord(tmp) && headTag == '*') {
                String body = builder.toString();
                builder.delete(0, builder.length());
                question.getBody().setContent(body);
                context.inContext(QuestionItemType.body);
                builder.append(getOptionContent(tmp));
                continue;
            }
            if (head > 'A' && head <= 'G' && !startWithWord(tmp) && headTag == '*') {
                String optionContent = builder.toString();
                String tag = StringUtil.char2String((char) (head - 1));
                headBack = head;
                question.getOptions().addOption(new Option.Builder()
                        .option(optionContent)
                        .tag(tag)
                        .build());
                context.inContext(QuestionItemType.option);
                builder.delete(0, builder.length());
                builder.append(getOptionContent(tmp));
                continue;
            }
            if (checkIsAnswerTitle(tmp)) {
                String optionContent = builder.toString();
                String tag = StringUtil.char2String((char) headBack);
                question.getOptions().addOption(new Option.Builder()
                        .option(optionContent)
                        .tag(tag)
                        .build());
                builder.delete(0, builder.length());
                builder.append(tmp.substring(2).trim());
                continue;
            }
            builder.append(tmp);
        }
        String answer = builder.toString().substring(5);
        if (answer.startsWith(":") || answer.startsWith("：")) {
            question.getAnswer().setContent(answer.substring(1).trim());
        } else {
            question.getAnswer().setContent(answer);
        }
        inContext(QuestionItemType.answer);
        return question;
    }

    private String getOptionContent(String s) {
        s = s.trim().substring(2).trim();
        if (s.startsWith(":") || s.startsWith("：")) {
            s = s.trim().substring(1).trim();
        }
        return s;
    }

    private void inContext(QuestionItemType type) {
        context.inContext(new QuestionItem(type));
    }
}
