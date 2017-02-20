package com.zgq.wokao.parser;

import com.zgq.wokao.model.paper.ExamPaper;
import com.zgq.wokao.model.paper.NormalExamPaper;
import com.zgq.wokao.parser.context.PaperContext;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by zgq on 2017/2/18.
 */

public class PaperParser extends BaseParser implements IPaperParser{

    private InputStream is;
    private PaperContext context;
    private ArrayList<TopicList> topicLists = new ArrayList<>();
    private NormalExamPaper examPaper = new NormalExamPaper();

    private ExamPaper parse(){
        return examPaper;
    }

    @Override
    public ExamPaper parse(InputStream is) {
        this.is = is;
        return parse();
    }

    

    class TopicList{
        private int type;
        private String content;

        public int getType() {
            return type;
        }

        public void setType(int type) {
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
