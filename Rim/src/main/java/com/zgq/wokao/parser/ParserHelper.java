package com.zgq.wokao.parser;

import com.zgq.wokao.entity.paper.NormalExamPaper;
import com.zgq.wokao.entity.paper.question.DiscussQuestion;
import com.zgq.wokao.entity.paper.question.FillInQuestion;
import com.zgq.wokao.entity.paper.question.IQuestion;
import com.zgq.wokao.entity.paper.question.MultChoQuestion;
import com.zgq.wokao.entity.paper.question.QuestionType;
import com.zgq.wokao.entity.paper.question.SglChoQuestion;
import com.zgq.wokao.entity.paper.question.TFQuestion;
import com.zgq.wokao.exception.ParseException;
import com.zgq.wokao.parser.formater.impl.MSDocFormater;

import java.io.ByteArrayInputStream;
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
//    private static String TAG = ParserHelper.class.getSimpleName();
//
//    private String fileString;
//
//    private ParserHelper() {
//
//    }
//
//    public static ParserHelper getInstance() {
//        return InstanceHolder.helper;
//    }
//
//    private static class InstanceHolder {
//        private static ParserHelper helper = new ParserHelper();
//    }
//
//    private enum FileFormat {
//        TXT, WORD, ERRORFORMAT;
//    }
//
//    private FileFormat checkFile(String filePath) throws FileNotFoundException, com.zgq.wokao.exception.ParseException {
//        if (filePath == null || filePath.equals("")) {
//            throw new com.zgq.wokao.exception.ParseException("路径不存在");
//        }
//        String lowerCase = filePath.toLowerCase();
//        if (lowerCase.endsWith(".txt")) {
//            return FileFormat.TXT;
//        } else if (lowerCase.endsWith(".doc") || lowerCase.endsWith(".docx")) {
//            return FileFormat.WORD;
//        }
//
//        if (!lowerCase.endsWith(".txt") && !lowerCase.endsWith(".doc") && !lowerCase.endsWith(".docx")) {
//            throw new com.zgq.wokao.exception.ParseException("文件格式错误");
//        }
//        File file = new File(filePath);
//        if (!file.exists()) {
//            throw new com.zgq.wokao.exception.ParseException("文件不存在");
//        }
//
//        return FileFormat.ERRORFORMAT;
//    }
//
//    public NormalExamPaper parse(String fileStr) throws FileNotFoundException,
//            com.zgq.wokao.exception.ParseException, Exception {
//        this.fileString = fileStr;
//        NormalExamPaper paper = null;
//        switch (checkFile(fileString)) {
//            case TXT:
//                File txtFile = new File(fileString);
//                paper =  parse(new FileInputStream(txtFile));
//                break;
//            case WORD:
//                String wordStr = MSDocFormater.getInstance().getContent(fileString);
//                paper =  parse(new ByteArrayInputStream(wordStr.getBytes()));
//                break;
//            default:
//                break;
//        }
//
//        if (checkPaperUseful(paper)){
//            return paper;
//        }
//        return null;
//    }
//
//    public NormalExamPaper parse(InputStream inputStream) throws ParseException {
//        if (inputStream == null) {
//            throw new com.zgq.wokao.exception.ParseException("请检查是否为空文件");
//        }
//        NormalExamPaper paper = new NormalExamPaper();
//        PaperParser paperParser = new PaperParser();
//        ArrayList<PaperParser.Topic> topics = paperParser.parse(inputStream);
//        if (topics == null || topics.size() == 0){
//            com.orhanobut.logger.Logger.d("parse no Topic");
//            return null;
//        }else{
//            com.orhanobut.logger.Logger.d(topics);
//        }
//
//        paper.setPaperInfo(paperParser.getInfo());
//        if (topics.size() == 0) {
//            throw new com.zgq.wokao.exception.ParseException("请检查大标题");
//        }
//        for (PaperParser.Topic tmp : topics) {
//            switch (tmp.getType()) {
//                case FILLIN:
//                    paper.setFillInQuestions(parseFillin(tmp));
//                    break;
//                case TF:
//                    paper.setTfQuestions(parseTF(tmp));
//                    break;
//                case SINGLECHOOSE:
//                    paper.setSglChoQuestions(parseSgl(tmp));
//                    break;
//                case MUTTICHOOSE:
//                    paper.setMultChoQuestions(parseMult(tmp));
//                    break;
//                case DISCUSS:
//                    paper.setDiscussQuestions(parseDis(tmp));
//                    break;
//                case NOTQUESTION:
//                    break;
//                default:
//                    break;
//            }
//        }
//        paper.getPaperInfo().setCreateDate(DateUtil.getCurrentDate());
//        paper.getPaperInfo().setId(UUIDUtil.getID());
//        initPaperData(paper);
//        return paper;
//    }
//
//    private boolean checkPaperUseful(NormalExamPaper paper){
//        if (paper == null || (
//        paper.getFillInQuestions().size() == 0 &&
//                paper.getTfQuestions().size() == 0 &&
//                paper.getSglChoQuestions().size() == 0 &&
//                paper.getMultChoQuestions().size() == 0 &&
//                paper.getDiscussQuestions().size() == 0)){
//            return false;
//        }
//        return true;
//    }
//
//    private void initPaperData(IExamPaper paper) {
//        //设置当前包含哪些题型
//        IPaperInfo info = paper.getPaperInfo();
//        if (paper.getFillInQuestions().size() != 0)
//            info.addQuestionType(QuestionType.FILLIN);
//        if (paper.getTfQuestions().size() != 0)
//            info.addQuestionType(QuestionType.TF);
//        if (paper.getSglChoQuestions().size() != 0)
//            info.addQuestionType(QuestionType.SINGLECHOOSE);
//        if (paper.getMultChoQuestions().size() != 0)
//            info.addQuestionType(QuestionType.MUTTICHOOSE);
//        if (paper.getDiscussQuestions().size() != 0)
//            info.addQuestionType(QuestionType.DISCUSS);
//        //默认加入学习计划
//        paper.getPaperInfo().setInSchedule(true);
//
//    }
//
//    private <T extends IQuestion> ArrayList<T> parseQuestion(QuestionType type, PaperParser.Topic resource) {
//        QuestionParser parser = new QuestionParser();
//        parser.setAdapter(type);
//        ArrayList<T> list = (ArrayList<T>) parser.parse(resource);
//        return list;
//    }
//
//    private RealmList<FillInQuestion> parseFillin(PaperParser.Topic resource) {
//        QuestionParser parser = new QuestionParser();
//        parser.setAdapter(QuestionType.FILLIN);
//        ArrayList<IQuestion> list = parser.parse(resource);
//        RealmList<FillInQuestion> results = new RealmList<>();
//        for (IQuestion tmp : list) {
//            results.add((FillInQuestion) tmp);
//        }
//        return results;
//    }
//
//    private RealmList<TFQuestion> parseTF(PaperParser.Topic resource) {
//        QuestionParser parser = new QuestionParser();
//        parser.setAdapter(QuestionType.TF);
//        ArrayList<IQuestion> list = parser.parse(resource);
//        RealmList<TFQuestion> results = new RealmList<>();
//        for (IQuestion tmp : list) {
//            results.add((TFQuestion) tmp);
//        }
//        return results;
//    }
//
//    private RealmList<SglChoQuestion> parseSgl(PaperParser.Topic resource) {
//        QuestionParser parser = new QuestionParser();
//        parser.setAdapter(QuestionType.SINGLECHOOSE);
//        ArrayList<IQuestion> list = parser.parse(resource);
//        RealmList<SglChoQuestion> results = new RealmList<>();
//        for (IQuestion tmp : list) {
//            results.add((SglChoQuestion) tmp);
//        }
//        return results;
//    }
//
//    private RealmList<MultChoQuestion> parseMult(PaperParser.Topic resource) {
//        QuestionParser parser = new QuestionParser();
//        parser.setAdapter(QuestionType.MUTTICHOOSE);
//        ArrayList<IQuestion> list = parser.parse(resource);
//        RealmList<MultChoQuestion> results = new RealmList<>();
//        for (IQuestion tmp : list) {
//            results.add((MultChoQuestion) tmp);
//        }
//        return results;
//    }
//
//    private RealmList<DiscussQuestion> parseDis(PaperParser.Topic resource) {
//        QuestionParser parser = new QuestionParser();
//        parser.setAdapter(QuestionType.DISCUSS);
//        ArrayList<IQuestion> list = parser.parse(resource);
//        RealmList<DiscussQuestion> results = new RealmList<>();
//        for (IQuestion tmp : list) {
//            results.add((DiscussQuestion) tmp);
//        }
//        return results;
//    }

}
