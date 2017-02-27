package com.zgq.wokao.parser;

import android.util.Xml;

import com.zgq.wokao.Util.UUIDUtil;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.paper.question.impl.DiscussIQuestion;
import com.zgq.wokao.model.paper.question.impl.FillInIQuestion;
import com.zgq.wokao.model.paper.question.impl.MultChoQuestion;
import com.zgq.wokao.model.paper.NormalExamPaper;
import com.zgq.wokao.model.paper.question.impl.SglChoQuestion;
import com.zgq.wokao.model.paper.question.option.Option;
import com.zgq.wokao.model.paper.question.impl.TFIQuestion;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;

import io.realm.RealmList;

/**
 * Created by zgq on 16-6-19.
 */
public class DataXml2ObjParser {
    private static DataXml2ObjParser dataXml2ObjParser;
    private XmlPullParser parser;

    private DataXml2ObjParser() {
    }

    public static DataXml2ObjParser getInstance() {
        if (dataXml2ObjParser == null) {
            dataXml2ObjParser = new DataXml2ObjParser();
        }
        return dataXml2ObjParser;
    }

    public NormalExamPaper parse(File xmlFile) throws Exception,ParseException {

        if (!xmlFile.exists() || xmlFile == null) return null;

        FileInputStream fileInputStream = new FileInputStream(xmlFile);

        NormalExamPaper normalExamPaper = new NormalExamPaper();
        RealmList<FillInIQuestion> fillInQuestions = new RealmList<>();
        RealmList<TFIQuestion> tfQuestions = new RealmList<>();
        RealmList<SglChoQuestion> sglChoQuestions = new RealmList<>();
        RealmList<MultChoQuestion> multChoQuestions = new RealmList<>();
        RealmList<DiscussIQuestion> discussQuestions = new RealmList<>();

        parser = Xml.newPullParser();
        parser.setInput(fileInputStream, "UTF-8");
        int event = parser.getEventType();
        while (event != XmlPullParser.END_DOCUMENT) {
            if (event == XmlPullParser.START_TAG) {
                if (XmlNodeInfo.fillInQuestionNode.equals(parser.getName())) {
                    fillInQuestions.add(parseFillInQuestion(parser));
                }
                if (XmlNodeInfo.tfQuestionNode.equals(parser.getName())) {
                    tfQuestions.add(parseTFQuestion(parser));
                }
                if (XmlNodeInfo.sglChoQuestionNode.equals(parser.getName())) {
                    sglChoQuestions.add(parseSglChoQuestion(parser));
                }
                if (XmlNodeInfo.multChoQuestionNode.equals(parser.getName())) {
                    multChoQuestions.add(parseMultChoQuestion(parser));
                }
                if (XmlNodeInfo.discussQuestionNode.equals(parser.getName())) {
                    discussQuestions.add(parseDiscussQuestion(parser));
                }
                if (XmlNodeInfo.examPaperTitleNode.equals(parser.getName())) {
                    normalExamPaper.getPaperInfo().setTitle(parser.nextText());
                }
                if (XmlNodeInfo.examPaperAuthorNode.equals(parser.getName())) {
                    normalExamPaper.getPaperInfo().setAuthor(parser.nextText());
                }
            }
            event = parser.next();
        }
        fileInputStream.close();
        normalExamPaper.setFillInQuestions(fillInQuestions);
        normalExamPaper.setTfQuestions(tfQuestions);
        normalExamPaper.setSglChoQuestions(sglChoQuestions);
        normalExamPaper.setMultChoQuestions(multChoQuestions);
        normalExamPaper.setDiscussQuestions(discussQuestions);

        normalExamPaper.getPaperInfo().setId(UUIDUtil.getID());
        return normalExamPaper;
    }

    private FillInIQuestion parseFillInQuestion(XmlPullParser pullParser) throws Exception {
        if (!XmlNodeInfo.fillInQuestionNode.equals(pullParser.getName())) return null;
        FillInIQuestion fillInQuestion = new FillInIQuestion.Builder().build();
//        Log.d("---------->>",pullParser.getName());
        int event = 0;
        while (true) {
            event = pullParser.next();
            if (event == XmlPullParser.END_TAG && pullParser.getName().equals(XmlNodeInfo.fillInQuestionNode))
                break;
            if (event == XmlPullParser.END_TAG) continue;
            if (event == XmlPullParser.START_TAG) {
                switch (pullParser.getName()) {
                    case XmlNodeInfo.idNode:
//                        String s = pullParser.nextText();
//                        Log.d("-------id",s);
                        fillInQuestion.getInfo().setId(Integer.parseInt(pullParser.nextText()));
                        continue;
                    case XmlNodeInfo.typeNode:
//                        String ss = pullParser.nextText();
//                        Log.d("-------type",ss);
                        fillInQuestion.getInfo().setType(changeIntToType(pullParser.nextText()));
                        continue;
                    case XmlNodeInfo.bodyNode:
//                        String sss = pullParser.nextText();
//                        Log.d("-------body",sss);
                        fillInQuestion.getBody().setContent((pullParser.nextText()));
                        continue;
                    case XmlNodeInfo.answerNode:
//                        String ssss = pullParser.nextText();
//                        Log.d("-------questionAnswer",ssss);
                        fillInQuestion.getAnswer().setContent(pullParser.nextText());
                }
            }
        }
        return fillInQuestion;
    }

    private TFIQuestion parseTFQuestion(XmlPullParser pullParser) throws Exception {
        if (!XmlNodeInfo.tfQuestionNode.equals(pullParser.getName())) return null;
        TFIQuestion tfQuestion = new TFIQuestion.Builder().build();
        int event = 0;
        while (true) {
            event = pullParser.next();
            if (event == XmlPullParser.END_TAG && pullParser.getName().equals(XmlNodeInfo.tfQuestionNode))
                break;
            if (event == XmlPullParser.END_TAG)
                continue;
            if (event == XmlPullParser.START_TAG) {
                switch (pullParser.getName()) {
                    case XmlNodeInfo.idNode:
//                        String s = pullParser.nextText();
//                        Log.d("-------id",s);
                        tfQuestion.getInfo().setId(Integer.parseInt(pullParser.nextText()));
                        continue;
                    case XmlNodeInfo.typeNode:
//                        String ss = pullParser.nextText();
//                        Log.d("-------type",ss);
                        tfQuestion.getInfo().setType(changeIntToType(pullParser.nextText()));
                        continue;
                    case XmlNodeInfo.bodyNode:
//                        String sss = pullParser.nextText();
//                        Log.d("-------body",sss);
                        tfQuestion.getBody().setContent(pullParser.nextText());
                        continue;
                    case XmlNodeInfo.answerNode:
                        tfQuestion.getAnswer().setContent(pullParser.nextText());
                }
            }
        }
        return tfQuestion;
    }

    private SglChoQuestion parseSglChoQuestion(XmlPullParser pullParser) throws Exception {
        if (!XmlNodeInfo.sglChoQuestionNode.equals(pullParser.getName())) return null;
        SglChoQuestion sglChoQuestion = new SglChoQuestion.Builder().build();
        int event = 0;
        while (true) {
            event = pullParser.next();
            if (event == XmlPullParser.END_TAG && pullParser.getName().equals(XmlNodeInfo.sglChoQuestionNode))
                break;
            if (event == XmlPullParser.END_TAG)
                continue;
            if (event == XmlPullParser.START_TAG) {
                switch (pullParser.getName()) {
                    case XmlNodeInfo.idNode:
                        sglChoQuestion.getInfo().setId(Integer.parseInt(pullParser.nextText()));
                        continue;
                    case XmlNodeInfo.typeNode:
                        sglChoQuestion.getInfo().setType(changeIntToType(pullParser.nextText()));
                        continue;
                    case XmlNodeInfo.bodyNode:
                        sglChoQuestion.getBody().setContent(pullParser.nextText());
                        continue;
                    case XmlNodeInfo.answerNode:
                        sglChoQuestion.getAnswer().setContent(pullParser.nextText());
                        continue;
                    case XmlNodeInfo.optionNode:
                        sglChoQuestion.getOptions().addOption(new Option.Builder().option(pullParser.nextText()).tag("").build());
                }
            }
        }
        return sglChoQuestion;
    }

    private MultChoQuestion parseMultChoQuestion(XmlPullParser pullParser) throws Exception {
        if (!XmlNodeInfo.multChoQuestionNode.equals(pullParser.getName())) return null;
        MultChoQuestion multChoQuestion = new MultChoQuestion.Builder().build();
        int event = 0;
        while (true) {
            event = pullParser.next();
            if (event == XmlPullParser.END_TAG && pullParser.getName().equals(XmlNodeInfo.multChoQuestionNode))
                break;
            if (event == XmlPullParser.END_TAG)
                continue;
            if (event == XmlPullParser.START_TAG) {
                switch (pullParser.getName()) {
                    case XmlNodeInfo.idNode:
                        multChoQuestion.getInfo().setId(Integer.parseInt(pullParser.nextText()));
                        continue;
                    case XmlNodeInfo.typeNode:
                        multChoQuestion.getInfo().setType(changeIntToType(pullParser.nextText()));
                        continue;
                    case XmlNodeInfo.bodyNode:
                        multChoQuestion.getBody().setContent(pullParser.nextText());
                        continue;
                    case XmlNodeInfo.answerNode:
                        multChoQuestion.getAnswer().setContent(pullParser.nextText());
                        continue;
                    case XmlNodeInfo.optionNode:
                        multChoQuestion.getOptions().addOption(new Option.Builder().option(pullParser.nextText()).tag("").build());
                }
            }
        }
        return multChoQuestion;
    }

    private DiscussIQuestion parseDiscussQuestion(XmlPullParser pullParser) throws Exception {
        if (!XmlNodeInfo.discussQuestionNode.equals(pullParser.getName())) return null;
        DiscussIQuestion discussQuestion = new DiscussIQuestion.Builder().build();
        int event = 0;
        while (true) {
            event = pullParser.next();
            if (event == XmlPullParser.END_TAG && pullParser.getName().equals(XmlNodeInfo.discussQuestionNode))
                break;
            if (event == XmlPullParser.END_TAG)
                continue;
            if (event == XmlPullParser.START_TAG) {
                switch (pullParser.getName()) {
                    case XmlNodeInfo.idNode:
                        discussQuestion.getInfo().setId(Integer.parseInt(pullParser.nextText()));
                        continue;
                    case XmlNodeInfo.typeNode:
                        discussQuestion.getInfo().setType(changeIntToType(pullParser.nextText()));
                        continue;
                    case XmlNodeInfo.bodyNode:
                        discussQuestion.getBody().setContent(pullParser.nextText());
                        continue;
                    case XmlNodeInfo.answerNode:
                        discussQuestion.getAnswer().setContent(pullParser.nextText());
                }
            }
        }
        return discussQuestion;
    }

    private QuestionType changeIntToType(String type){
        switch (type){
            case "fill_in_question":
                return QuestionType.fillin;
            case "tf_question":
                return QuestionType.tf;
            case "sglcho_question":
                return QuestionType.sglc;
            case "multcho_question":
                return QuestionType.mtlc;
            case "discuss_question":
                return QuestionType.disc;
            default:
                return null;
        }
    }
}
