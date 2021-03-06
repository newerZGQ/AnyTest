package com.zgq.wokao.parser.adapter.impl;

import com.orhanobut.logger.Logger;
import com.zgq.wokao.entity.paper.question.Answer;
import com.zgq.wokao.entity.paper.question.Option;
import com.zgq.wokao.entity.paper.question.Options;
import com.zgq.wokao.entity.paper.question.QuestionBody;
import com.zgq.wokao.entity.paper.question.QuestionInfo;
import com.zgq.wokao.entity.paper.question.QuestionRecord;
import com.zgq.wokao.entity.paper.question.QuestionType;
import com.zgq.wokao.entity.paper.question.SglChoQuestion;
import com.zgq.wokao.parser.adapter.BaseAdapter;
import com.zgq.wokao.parser.adapter.ISglChoAdapter;
import com.zgq.wokao.parser.context.QuestionContext;
import com.zgq.wokao.parser.context.item.QuestionItem;
import com.zgq.wokao.parser.context.item.QuestionItemType;
import com.zgq.wokao.util.ListUtil;

import java.util.ArrayList;
import java.util.UUID;

import io.realm.RealmList;

public class SglChoAdapter extends BaseAdapter implements ISglChoAdapter {
    private QuestionType type = QuestionType.SINGLECHOOSE;
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
        Logger.d("parseTopic sgkcho" + resource);
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
        Logger.d("parseTopic sglcho single " + questionRes);
        SglChoQuestion question = SglChoQuestion.builder()
                .id(UUID.randomUUID().toString())
                .answer(Answer.builder().id(UUID.randomUUID().toString()).build())
                .body(QuestionBody.builder().id(UUID.randomUUID().toString()).build())
                .info(QuestionInfo.builder().id(UUID.randomUUID().toString()).build())
                .record(QuestionRecord.builder().id(UUID.randomUUID().toString()).build())
                .options(Options.builder()
                        .id(UUID.randomUUID().toString())
                        .optionList(new RealmList<>())
                        .build())
                .build();
        question.getInfo().setIndex(number);
        question.getInfo().setId(UUID.randomUUID().toString());
        question.getInfo().setType(QuestionType.SINGLECHOOSE.getValue());
        inContext(QuestionItemType.number);
        String[] resArray = trimNum(questionRes).split("\n");
        StringBuilder builder = new StringBuilder();
        int headBack = 0;
        for (String tmp : resArray) {
            tmp = tmp.trim();
            char head = tmp.toUpperCase().charAt(1);
            char headTag = tmp.toUpperCase().charAt(0);
            if (head == 'A' && headTag == '*') {
                String body = builder.toString();
                builder.delete(0, builder.length());
                question.getBody().setContent(body);
                context.inContext(QuestionItemType.body);
                builder.append(getOptionContent(tmp));
                continue;
            }
            if (head > 'A' && head <= 'G' && headTag == '*') {
                String optionContent = builder.toString();
                String tag = String.valueOf((char) (head - 1));
                headBack = head;
                question.getOptions().getOptionList().add(Option.builder()
                        .id(UUID.randomUUID().toString())
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
                String tag = String.valueOf((char) headBack);
                question.getOptions().getOptionList().add(Option.builder()
                        .id(UUID.randomUUID().toString())
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
