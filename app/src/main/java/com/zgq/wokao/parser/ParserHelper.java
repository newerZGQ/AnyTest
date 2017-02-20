package com.zgq.wokao.parser;

import com.zgq.wokao.model.paper.DiscussQuestion;
import com.zgq.wokao.model.paper.FillInQuestion;
import com.zgq.wokao.model.paper.MultChoQuestion;
import com.zgq.wokao.model.paper.NormalExamPaper;
import com.zgq.wokao.model.paper.Question;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.paper.SglChoQuestion;
import com.zgq.wokao.model.paper.TFQuestion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    public NormalExamPaper parse(String fileStr) throws FileNotFoundException, com.zgq.wokao.exception.ParseException {
        NormalExamPaper paper = new NormalExamPaper();
        this.fileString = fileStr;
        if (fileString == null || fileString.equals("")){
            throw new com.zgq.wokao.exception.ParseException("路径不存在");
        }
        File file = new File(fileString);
        if (!file.exists()){
            throw new com.zgq.wokao.exception.ParseException("文件不存在");
        }
        PaperParser paperParser = new PaperParser();
        ArrayList<PaperParser.Topic> topics = paperParser.parse(new FileInputStream(file));
        paper.setPaperInfo(paperParser.getInfo());
        if (topics.size() == 0){
            throw new com.zgq.wokao.exception.ParseException("请检查大标题");
        }
        for (PaperParser.Topic tmp : topics){
            switch (tmp.getType()){
                case fillin:
                    paper.setFillInQuestions(parseFillin(tmp));
                    break;
                case tf:
                    paper.setTfQuestions(parseTF(tmp));
                    break;
                case sglc:
                    paper.setSglChoQuestions(parseSgl(tmp));
                    break;
                case mtlc:
                    paper.setMultChoQuestions(parseMult(tmp));
                    break;
                case disc:
                    paper.setDiscussQuestions(parseDis(tmp));
                    break;
                case notQst:
                    break;
            }
        }
        return paper;
    }

    private <T extends Question> ArrayList<T> parseQuestion(QuestionType type,PaperParser.Topic resource){
        QuestionParser parser = new QuestionParser();
        parser.setAdapter(type);
        ArrayList<T> list = (ArrayList<T>) parser.parse(resource);
        return list;
    }

    private RealmList<FillInQuestion> parseFillin(PaperParser.Topic resource){
        QuestionParser parser = new QuestionParser();
        parser.setAdapter(QuestionType.fillin);
        ArrayList<Question> list = parser.parse(resource);
        RealmList<FillInQuestion> results = new RealmList<>();
        for (Question tmp: list){
            results.add((FillInQuestion)tmp);
        }
        return results;
    }
    private RealmList<TFQuestion> parseTF(PaperParser.Topic resource){
        QuestionParser parser = new QuestionParser();
        parser.setAdapter(QuestionType.tf);
        ArrayList<Question> list = parser.parse(resource);
        RealmList<TFQuestion> results = new RealmList<>();
        for (Question tmp: list){
            results.add((TFQuestion)tmp);
        }
        return results;
    }
    private RealmList<SglChoQuestion> parseSgl(PaperParser.Topic resource){
        QuestionParser parser = new QuestionParser();
        parser.setAdapter(QuestionType.sglc);
        ArrayList<Question> list = parser.parse(resource);
        RealmList<SglChoQuestion> results = new RealmList<>();
        for (Question tmp: list){
            results.add((SglChoQuestion)tmp);
        }
        return results;
    }
    private RealmList<MultChoQuestion> parseMult(PaperParser.Topic resource){
        QuestionParser parser = new QuestionParser();
        parser.setAdapter(QuestionType.mtlc);
        ArrayList<Question> list = parser.parse(resource);
        RealmList<MultChoQuestion> results = new RealmList<>();
        for (Question tmp: list){
            results.add((MultChoQuestion) tmp);
        }
        return results;
    }
    private RealmList<DiscussQuestion> parseDis(PaperParser.Topic resource){
        QuestionParser parser = new QuestionParser();
        parser.setAdapter(QuestionType.disc);
        ArrayList<Question> list = parser.parse(resource);
        RealmList<DiscussQuestion> results = new RealmList<>();
        for (Question tmp: list){
            results.add((DiscussQuestion) tmp);
        }
        return results;
    }

}
