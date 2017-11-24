package com.zgq.wokao.parser;

import com.zgq.wokao.entity.paper.NormalExamPaper;
import com.zgq.wokao.entity.paper.info.DailyRecord;
import com.zgq.wokao.entity.paper.info.ExamPaperInfo;
import com.zgq.wokao.entity.paper.info.Schedule;
import com.zgq.wokao.entity.paper.question.DiscussQuestion;
import com.zgq.wokao.entity.paper.question.FillInQuestion;
import com.zgq.wokao.entity.paper.question.IQuestion;
import com.zgq.wokao.entity.paper.question.MultChoQuestion;
import com.zgq.wokao.entity.paper.question.QuestionType;
import com.zgq.wokao.entity.paper.question.SglChoQuestion;
import com.zgq.wokao.entity.paper.question.TFQuestion;
import com.zgq.wokao.exception.ParseException;
import com.zgq.wokao.parser.context.PaperContext;
import com.zgq.wokao.parser.context.item.PaperItemType;
import com.zgq.wokao.util.DateUtil;
import com.zgq.wokao.util.UUIDUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import io.realm.RealmList;


/**
 * Created by zgq on 2017/2/18.
 */

public class PaperParser extends BaseParser implements IPaperParser {

    private InputStream is;
    private PaperContext context = new PaperContext();
    private ArrayList<Topic> topicLists = new ArrayList<>();
    private NormalExamPaper paper;

    private int contextLength = 5;

    public PaperParser() {
        initParam();
    }

    public PaperParser initParam() {
        context.init(contextLength);
        RealmList<DailyRecord> dailyRecords = new RealmList<>();
        dailyRecords.add(DailyRecord.builder().date(DateUtil.getCurrentDate()).build());
        paper = NormalExamPaper.builder()
                .paperInfo(
                        ExamPaperInfo.builder().schedule(
                                Schedule.builder().dailyRecords(dailyRecords).build()
                        ).build()
                )
                .build();
        return this;
    }

    private ArrayList<Topic> parse() throws ParseException, IOException {
        if (is == null) {
            throw new ParseException("输入流为空");
        }
        String line = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        boolean hasTitle = true;
        boolean hasAuthor = true;
        QuestionType topicType = QuestionType.FILLIN;
        StringBuilder ctBuilder = new StringBuilder();
        while ((line = br.readLine()) != null) {
            if (line.equals("")) continue;
            if (hasTitle && getTopicType(line) == QuestionType.NOTQUESTION) {
                parseTitle(line);
                context.inContext(PaperItemType.title);
                continue;
            }
            if (hasAuthor && getTopicType(line) == QuestionType.NOTQUESTION) {
                parseAuthor(line);
                context.inContext(PaperItemType.author);
                continue;
            }
            if (getTopicType(line) != QuestionType.NOTQUESTION) {

                hasTitle = false;
                hasAuthor = false;
                //存储上个循环的结果
                Topic topic = new Topic(topicType, ctBuilder.toString());
                topicLists.add(topic);
                //刷新数据
                topicType = getTopicType(line);
                ctBuilder.delete(0, ctBuilder.length());
                context.inContext(PaperItemType.topic);
            } else {
                context.inContext(PaperItemType.other);
                ctBuilder.append("\n" + line);
            }
        }

        if (ctBuilder.toString().equals("")) {
            return topicLists;
        }
        Topic topic = new Topic(topicType, ctBuilder.toString());
        topicLists.add(topic);
        return topicLists;
    }

    @Override
    public ArrayList<Topic> parseTopic(InputStream is) {
        this.is = is;
        try {
            return parse();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private QuestionType getTopicType(String line) {
        if (isFillInTitle(line) &&
                isTopicNumber(line))
            return QuestionType.FILLIN;
        if (isTFTitle(line) &&
                isTopicNumber(line))
            return QuestionType.TF;
        if (isSglChoTitle(line)
                && isTopicNumber(line))
            return QuestionType.SINGLECHOOSE;
        if (isMutiChoTitle(line)
                && isTopicNumber(line))
            return QuestionType.MUTTICHOOSE;
        if ((isDiscusTitle(line))
                && isTopicNumber(line))
            return QuestionType.DISCUSS;
        return QuestionType.NOTQUESTION;
    }

    private boolean isStartWithNumber(String s) {
        if (s.startsWith("一") ||
                s.startsWith("二") ||
                s.startsWith("三") ||
                s.startsWith("四") ||
                s.startsWith("五") ||
                s.startsWith("六") ||
                s.startsWith("七") ||
                s.startsWith("八") ||
                s.startsWith("九") ||
                s.startsWith("十") ||
                s.startsWith("1") ||
                s.startsWith("2") ||
                s.startsWith("3") ||
                s.startsWith("4") ||
                s.startsWith("5") ||
                s.startsWith("6") ||
                s.startsWith("7") ||
                s.startsWith("8") ||
                s.startsWith("9") ||
                s.startsWith("10") ||
                s.startsWith("I") ||
                s.startsWith("II") ||
                s.startsWith("III") ||
                s.startsWith("IV") ||
                s.startsWith("V")
                ) {
            return true;
        }
        return false;
    }

    private void parseTitle(String s) {
        if (getTopicType(s) == QuestionType.NOTQUESTION &&
                !isAuthorTitle(s)) {
            paper.getPaperInfo().setTitle(s);
        }
    }

    private void parseAuthor(String s) {
        if (getTopicType(s) == QuestionType.NOTQUESTION &&
                isAuthorTitle(s)) {
            paper.getPaperInfo().setAuthor(s);
        }
    }

    private boolean isTopicNumber(String s) {
        if (isStartWithNumber(s)) {
            return true;
        }
        if (s.startsWith("(") ||
                s.startsWith("（")) {
            s = s.substring(1);
            if (isStartWithNumber(s)) {
                return true;
            }
        }
        return false;
    }


    public NormalExamPaper parse(InputStream inputStream) throws ParseException {
        ArrayList<PaperParser.Topic> topics = parseTopic(inputStream);
        if (topics == null || topics.size() == 0) {
            return null;
        }

        if (topics.size() == 0) {
            throw new com.zgq.wokao.exception.ParseException("请检查大标题");
        }
        for (PaperParser.Topic tmp : topics) {
            switch (tmp.getType()) {
                case FILLIN:
                    paper.setFillInQuestions(parseFillin(tmp));
                    break;
                case TF:
                    paper.setTfQuestions(parseTF(tmp));
                    break;
                case SINGLECHOOSE:
                    paper.setSglChoQuestions(parseSgl(tmp));
                    break;
                case MUTTICHOOSE:
                    paper.setMultChoQuestions(parseMult(tmp));
                    break;
                case DISCUSS:
                    paper.setDiscussQuestions(parseDis(tmp));
                    break;
                case NOTQUESTION:
                    break;
                default:
                    break;
            }
        }
        paper.getPaperInfo().setCreateDate(DateUtil.getCurrentDate());
        paper.getPaperInfo().setId(UUIDUtil.getID());
        initPaperData(paper);
        return paper;
    }

    private void initPaperData(NormalExamPaper paper) {
        //默认加入学习计划
        paper.getPaperInfo().getSchedule().setInSked(true);
    }

    private RealmList<FillInQuestion> parseFillin(PaperParser.Topic resource) {
        QuestionParser parser = new QuestionParser();
        parser.setAdapter(QuestionType.FILLIN);
        ArrayList<IQuestion> list = parser.parse(resource);
        RealmList<FillInQuestion> results = new RealmList<>();
        for (IQuestion tmp : list) {
            results.add((FillInQuestion) tmp);
        }
        return results;
    }

    private RealmList<TFQuestion> parseTF(PaperParser.Topic resource) {
        QuestionParser parser = new QuestionParser();
        parser.setAdapter(QuestionType.TF);
        ArrayList<IQuestion> list = parser.parse(resource);
        RealmList<TFQuestion> results = new RealmList<>();
        for (IQuestion tmp : list) {
            results.add((TFQuestion) tmp);
        }
        return results;
    }

    private RealmList<SglChoQuestion> parseSgl(PaperParser.Topic resource) {
        QuestionParser parser = new QuestionParser();
        parser.setAdapter(QuestionType.SINGLECHOOSE);
        ArrayList<IQuestion> list = parser.parse(resource);
        RealmList<SglChoQuestion> results = new RealmList<>();
        for (IQuestion tmp : list) {
            results.add((SglChoQuestion) tmp);
        }
        return results;
    }

    private RealmList<MultChoQuestion> parseMult(PaperParser.Topic resource) {
        QuestionParser parser = new QuestionParser();
        parser.setAdapter(QuestionType.MUTTICHOOSE);
        ArrayList<IQuestion> list = parser.parse(resource);
        RealmList<MultChoQuestion> results = new RealmList<>();
        for (IQuestion tmp : list) {
            results.add((MultChoQuestion) tmp);
        }
        return results;
    }

    private RealmList<DiscussQuestion> parseDis(PaperParser.Topic resource) {
        QuestionParser parser = new QuestionParser();
        parser.setAdapter(QuestionType.DISCUSS);
        ArrayList<IQuestion> list = parser.parse(resource);
        RealmList<DiscussQuestion> results = new RealmList<>();
        for (IQuestion tmp : list) {
            results.add((DiscussQuestion) tmp);
        }
        return results;
    }


    public static class Topic {
        private QuestionType type;
        private String content;

        public Topic(QuestionType type, String content) {
            this.type = type;
            this.content = content;
        }

        public QuestionType getType() {
            return type;
        }

        public void setType(QuestionType type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
