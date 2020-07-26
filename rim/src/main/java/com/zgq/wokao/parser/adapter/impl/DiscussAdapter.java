package com.zgq.wokao.parser.adapter.impl;

import com.zgq.wokao.common.utils.ListUtil;
import com.zgq.wokao.entity.paper.question.Answer;
import com.zgq.wokao.entity.paper.question.DiscussQuestion;
import com.zgq.wokao.entity.paper.question.QuestionBody;
import com.zgq.wokao.entity.paper.question.QuestionInfo;
import com.zgq.wokao.entity.paper.question.QuestionRecord;
import com.zgq.wokao.entity.paper.question.QuestionType;
import com.zgq.wokao.parser.adapter.BaseAdapter;
import com.zgq.wokao.parser.adapter.IDiscussAdapter;
import com.zgq.wokao.parser.context.QuestionContext;
import com.zgq.wokao.parser.context.item.QuestionItem;
import com.zgq.wokao.parser.context.item.QuestionItemType;

import java.util.ArrayList;
import java.util.UUID;

public class DiscussAdapter extends BaseAdapter implements IDiscussAdapter {

    private QuestionType type = QuestionType.DISCUSS;
    ArrayList<String> content = new ArrayList<>();
    QuestionContext context = new QuestionContext();
    private ArrayList<DiscussQuestion> results = new ArrayList<>();

    public DiscussAdapter() {
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
    public ArrayList<DiscussQuestion> parse(String resource) {
        String[] strings = resource.split("\n");
        content = (ArrayList<String>) ListUtil.array2list(strings);
        parseRes(content);
        return results;
    }

    private ArrayList<DiscussQuestion> parseRes(ArrayList<String> content) {
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
                if (bodyString.equals("")) {

                } else {
                    DiscussQuestion question = parseSingle(number, bodyString);
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

        DiscussQuestion question = parseSingle(number, builder.toString());
        if (question != null) {
            results.add(question);
        }
        return results;
    }

    private DiscussQuestion parseSingle(int number, String questionRes) {
        DiscussQuestion question = DiscussQuestion.builder()
                .id(UUID.randomUUID().toString())
                .answer(Answer.builder().id(UUID.randomUUID().toString()).build())
                .body(QuestionBody.builder().id(UUID.randomUUID().toString()).build())
                .info(QuestionInfo.builder().id(UUID.randomUUID().toString()).build())
                .record(QuestionRecord.builder().id(UUID.randomUUID().toString()).build())
                .build();
        question.getInfo().setIndex(number);
        question.getInfo().setId(UUID.randomUUID().toString());
        question.getInfo().setType(QuestionType.DISCUSS.getValue());
        inContext(QuestionItemType.number);
        String[] resArray = trimNum(questionRes).split("\n");
        StringBuilder builder = new StringBuilder();
        for (String tmp : resArray) {
            tmp = tmp.trim();
            if (checkIsAnswerTitle(tmp)) {
                question.getBody().setContent(builder.toString());
                inContext(QuestionItemType.body);

                String answerTmp = parseRealAnswer(tmp);

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
