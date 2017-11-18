package com.zgq.wokaofree.parser;

import com.zgq.wokaofree.exception.ParseException;
import com.zgq.wokaofree.model.paper.QuestionType;
import com.zgq.wokaofree.model.paper.info.ExamPaperInfo;
import com.zgq.wokaofree.parser.context.PaperContext;
import com.zgq.wokaofree.parser.context.item.PaperItemType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 * Created by zgq on 2017/2/18.
 */

public class PaperParser extends BaseParser implements IPaperParser {

    private InputStream is;
    private PaperContext context = new PaperContext();
    private ArrayList<Topic> topicLists = new ArrayList<>();
    private ExamPaperInfo info = new ExamPaperInfo();

    private int contextLength = 5;

    public PaperParser() {
        initParam();
    }

    public PaperParser initParam() {
        context.init(contextLength);
        return this;
    }

    public ArrayList<Topic> getTopicLists() {
        return topicLists;
    }

    public ExamPaperInfo getInfo() {
        return info;
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

        if (ctBuilder.toString().equals("")){
            return topicLists;
        }
        Topic topic = new Topic(topicType, ctBuilder.toString());
        topicLists.add(topic);
        return topicLists;
    }

    @Override
    public ArrayList<Topic> parse(InputStream is) {
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
            info.setTitle(s);
        }
    }

    private void parseAuthor(String s) {
        if (getTopicType(s) == QuestionType.NOTQUESTION &&
                isAuthorTitle(s)) {
            info.setAuthor(s);
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
