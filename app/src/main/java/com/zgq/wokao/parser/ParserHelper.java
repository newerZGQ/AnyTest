package com.zgq.wokao.parser;

import com.zgq.wokao.Util.DateUtil;
import com.zgq.wokao.exception.ParseException;
import com.zgq.wokao.model.paper.NormalIExamPaper;
import com.zgq.wokao.model.paper.question.impl.DiscussQuestion;
import com.zgq.wokao.model.paper.question.impl.FillInQuestion;
import com.zgq.wokao.model.paper.question.impl.MultChoQuestion;
import com.zgq.wokao.model.paper.question.impl.SglChoQuestion;
import com.zgq.wokao.model.paper.question.impl.TFQuestion;
import com.zgq.wokao.model.paper.question.IQuestion;
import com.zgq.wokao.model.paper.QuestionType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import io.realm.RealmList;

/**
 * Created by zgq on 2017/2/20.
 */

public class ParserHelper {
    private String fileString;
    private ParserHelper(){

    }
    public static ParserHelper getInstance(){
        return InstanceHolder.helper;
    }
    private static class InstanceHolder{
        private static ParserHelper helper = new ParserHelper();
    }
    public NormalIExamPaper parse(String fileStr) throws FileNotFoundException, com.zgq.wokao.exception.ParseException {
        NormalIExamPaper paper = new NormalIExamPaper();
        this.fileString = fileStr;
        if (fileString == null || fileString.equals("")){
            throw new com.zgq.wokao.exception.ParseException("路径不存在");
        }
        File file = new File(fileString);
        if (!file.exists()){
            throw new com.zgq.wokao.exception.ParseException("文件不存在");
        }
//        PaperParser paperParser = new PaperParser();
        return parse(new FileInputStream(file));
    }

    public NormalIExamPaper parse(InputStream inputStream) throws ParseException {
        if (inputStream == null){
            throw new com.zgq.wokao.exception.ParseException("请检查是否为空文件");
        }
        NormalIExamPaper paper = new NormalIExamPaper();
        PaperParser paperParser = new PaperParser();
        ArrayList<PaperParser.Topic> topics = paperParser.parse(inputStream);
        paper.setPaperInfo(paperParser.getInfo());
        if (topics.size() == 0){
            throw new com.zgq.wokao.exception.ParseException("请检查大标题");
        }
        for (PaperParser.Topic tmp : topics){
            switch (tmp.getType().getIndex()){
                case QuestionType.fillin_index:
                    paper.setFillInQuestions(parseFillin(tmp));
                    break;
                case QuestionType.tf_index:
                    paper.setTfQuestions(parseTF(tmp));
                    break;
                case QuestionType.sglc_index:
                    paper.setSglChoQuestions(parseSgl(tmp));
                    break;
                case QuestionType.mtlc_index:
                    paper.setMultChoQuestions(parseMult(tmp));
                    break;
                case QuestionType.disc_index:
                    paper.setDiscussQuestions(parseDis(tmp));
                    break;
                case QuestionType.noqst_index:
                    break;
            }
        }
        paper.getPaperInfo().setCreateDate(DateUtil.getCurrentDate());
        return paper;
    }

    private <T extends IQuestion> ArrayList<T> parseQuestion(QuestionType type, PaperParser.Topic resource){
        QuestionParser parser = new QuestionParser();
        parser.setAdapter(type);
        ArrayList<T> list = (ArrayList<T>) parser.parse(resource);
        return list;
    }

    private RealmList<FillInQuestion> parseFillin(PaperParser.Topic resource){
        QuestionParser parser = new QuestionParser();
        parser.setAdapter(QuestionType.fillin);
        ArrayList<IQuestion> list = parser.parse(resource);
        RealmList<FillInQuestion> results = new RealmList<>();
        for (IQuestion tmp: list){
            results.add((FillInQuestion)tmp);
        }
        return results;
    }
    private RealmList<TFQuestion> parseTF(PaperParser.Topic resource){
        QuestionParser parser = new QuestionParser();
        parser.setAdapter(QuestionType.tf);
        ArrayList<IQuestion> list = parser.parse(resource);
        RealmList<TFQuestion> results = new RealmList<>();
        for (IQuestion tmp: list){
            results.add((TFQuestion)tmp);
        }
        return results;
    }
    private RealmList<SglChoQuestion> parseSgl(PaperParser.Topic resource){
        QuestionParser parser = new QuestionParser();
        parser.setAdapter(QuestionType.sglc);
        ArrayList<IQuestion> list = parser.parse(resource);
        RealmList<SglChoQuestion> results = new RealmList<>();
        for (IQuestion tmp: list){
            results.add((SglChoQuestion)tmp);
        }
        return results;
    }
    private RealmList<MultChoQuestion> parseMult(PaperParser.Topic resource){
        QuestionParser parser = new QuestionParser();
        parser.setAdapter(QuestionType.mtlc);
        ArrayList<IQuestion> list = parser.parse(resource);
        RealmList<MultChoQuestion> results = new RealmList<>();
        for (IQuestion tmp: list){
            results.add((MultChoQuestion) tmp);
        }
        return results;
    }
    private RealmList<DiscussQuestion> parseDis(PaperParser.Topic resource){
        QuestionParser parser = new QuestionParser();
        parser.setAdapter(QuestionType.disc);
        ArrayList<IQuestion> list = parser.parse(resource);
        RealmList<DiscussQuestion> results = new RealmList<>();
        for (IQuestion tmp: list){
            results.add((DiscussQuestion) tmp);
        }
        return results;
    }

}
