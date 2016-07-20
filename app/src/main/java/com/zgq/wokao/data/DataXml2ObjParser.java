package com.zgq.wokao.data;

import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.util.ArrayList;

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
        RealmList<FillInQuestion> fillInQuestions = new RealmList<>();
        RealmList<TFQuestion> tfQuestions = new RealmList<>();
        RealmList<SglChoQuestion> sglChoQuestions = new RealmList<>();
        RealmList<MultChoQuestion> multChoQuestions = new RealmList<>();
        RealmList<DiscussQuestion> discussQuestions = new RealmList<>();

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

        return normalExamPaper;
    }

    private FillInQuestion parseFillInQuestion(XmlPullParser pullParser) throws Exception {
        if (!XmlNodeInfo.fillInQuestionNode.equals(pullParser.getName())) return null;
        FillInQuestion fillInQuestion = new FillInQuestion();
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
                        fillInQuestion.setId(Integer.parseInt(pullParser.nextText()));
                        continue;
                    case XmlNodeInfo.typeNode:
//                        String ss = pullParser.nextText();
//                        Log.d("-------type",ss);
                        fillInQuestion.setType(pullParser.nextText());
                        continue;
                    case XmlNodeInfo.bodyNode:
//                        String sss = pullParser.nextText();
//                        Log.d("-------body",sss);
                        fillInQuestion.setBody(pullParser.nextText());
                        continue;
                    case XmlNodeInfo.answerNode:
//                        String ssss = pullParser.nextText();
//                        Log.d("-------questionAnswer",ssss);
                        fillInQuestion.setAnswer(pullParser.nextText());
                }
            }
        }
        return fillInQuestion;
    }

    private TFQuestion parseTFQuestion(XmlPullParser pullParser) throws Exception {
        if (!XmlNodeInfo.tfQuestionNode.equals(pullParser.getName())) return null;
        TFQuestion tfQuestion = new TFQuestion();
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
                        tfQuestion.setId(Integer.parseInt(pullParser.nextText()));
                        continue;
                    case XmlNodeInfo.typeNode:
//                        String ss = pullParser.nextText();
//                        Log.d("-------type",ss);
                        tfQuestion.setType(pullParser.nextText());
                        continue;
                    case XmlNodeInfo.bodyNode:
//                        String sss = pullParser.nextText();
//                        Log.d("-------body",sss);
                        tfQuestion.setBody(pullParser.nextText());
                        continue;
                    case XmlNodeInfo.answerNode:
                        tfQuestion.setAnswer(pullParser.nextText());
                }
            }
        }
        return tfQuestion;
    }

    private SglChoQuestion parseSglChoQuestion(XmlPullParser pullParser) throws Exception {
        if (!XmlNodeInfo.sglChoQuestionNode.equals(pullParser.getName())) return null;
        SglChoQuestion sglChoQuestion = new SglChoQuestion();
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
                        sglChoQuestion.setId(Integer.parseInt(pullParser.nextText()));
                        continue;
                    case XmlNodeInfo.typeNode:
                        sglChoQuestion.setType(pullParser.nextText());
                        continue;
                    case XmlNodeInfo.bodyNode:
                        sglChoQuestion.setBody(pullParser.nextText());
                        continue;
                    case XmlNodeInfo.answerNode:
                        sglChoQuestion.setAnswer(pullParser.nextText());
                        continue;
                    case XmlNodeInfo.optionNode:
                        sglChoQuestion.addOption(new Option(pullParser.nextText()));
                }
            }
        }
        return sglChoQuestion;
    }

    private MultChoQuestion parseMultChoQuestion(XmlPullParser pullParser) throws Exception {
        if (!XmlNodeInfo.multChoQuestionNode.equals(pullParser.getName())) return null;
        MultChoQuestion multChoQuestion = new MultChoQuestion();
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
                        multChoQuestion.setId(Integer.parseInt(pullParser.nextText()));
                        continue;
                    case XmlNodeInfo.typeNode:
                        multChoQuestion.setType(pullParser.nextText());
                        continue;
                    case XmlNodeInfo.bodyNode:
                        multChoQuestion.setBody(pullParser.nextText());
                        continue;
                    case XmlNodeInfo.answerNode:
                        multChoQuestion.setAnswer(pullParser.nextText());
                        continue;
                    case XmlNodeInfo.optionNode:
                        multChoQuestion.addOption(new Option(pullParser.nextText()));
                }
            }
        }
        return multChoQuestion;
    }

    private DiscussQuestion parseDiscussQuestion(XmlPullParser pullParser) throws Exception {
        if (!XmlNodeInfo.discussQuestionNode.equals(pullParser.getName())) return null;
        DiscussQuestion discussQuestion = new DiscussQuestion();
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
                        discussQuestion.setId(Integer.parseInt(pullParser.nextText()));
                        continue;
                    case XmlNodeInfo.typeNode:
                        discussQuestion.setType(pullParser.nextText());
                        continue;
                    case XmlNodeInfo.bodyNode:
                        discussQuestion.setBody(pullParser.nextText());
                        continue;
                    case XmlNodeInfo.answerNode:
                        discussQuestion.setAnswer(pullParser.nextText());
                }
            }
        }
        return discussQuestion;
    }
}
