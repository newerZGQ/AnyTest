package com.zgq.wokao.data;

import android.util.Log;

import com.zgq.wokao.Util.FileUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by zgq on 16-6-18.
 */
public class DataTxt2XmlParser {

    private String examPaperNode = "exam_paper";
    private String examPaperTitleNode = "title";
    private String examPaperAuthorNode = "author";
    private String fillInQuestionNode = "fill_in_question";
    private String tfQuestionNodeNode = "tf_question";
    private String sglChoQuestionNode = "sglcho_question";
    private String multChoQuestionNode = "multcho_question";
    private String discussQuestionNode = "discuss_question";
    private String idNode = "id";
    private String typeNode = "typeNode";
    private String bodyNode = "body";
    private String answerNode = "questionAnswer";
    private String optionNode = "option";


    private final int FILLINQUESTION = 1;
    private final int TFQUESTION = 2;
    private final int SGLCHOQUESTION = 3;
    private final int MULTCHOQUESTION = 4;
    private final int DISCUSSQUESTION = 5;


    private File txtFile;
    private File xmlFile;
    private static DataTxt2XmlParser dataTxt2XmlParser;

    private DataTxt2XmlParser() {
    }

    public static DataTxt2XmlParser getInstance() {
        if (dataTxt2XmlParser == null) {
            dataTxt2XmlParser = new DataTxt2XmlParser();
        }
        return dataTxt2XmlParser;
    }

    //解析txt文件，生成xml文件并返回xml文件名称
    public String parse(File txt, File xml) throws IOException {
        if (!FileUtil.isTxtFile(txt)) return null;
        if (!FileUtil.isXmlFile(xml)) return null;
        txtFile = txt;
        xmlFile = xml;
        String txtName = txtFile.getName();
        getPagerInfo();
        getQuestions();
//        Log.d("DataTxt2XmlParser", "wan");
        return xmlFile.getName();
    }

    private void getPagerInfo() {
        if (!txtFile.exists()) return;
        if (!xmlFile.exists()) return;
        int count = 0;

        BufferedReader br;
        String line = null;

        FileReader fileReader = null;
        FileWriter fileWriter = null;

        try {
            fileReader = new FileReader(txtFile);
            fileWriter = new FileWriter(xmlFile,true);
            br = new BufferedReader(fileReader);

            fileWriter.write("<?xml version=\"1.0\"?>\n");
            fileWriter.write("<"+examPaperNode+">"+"\n");

            while ((line = br.readLine()) != null) {
                Log.d("----------->>", line);
                if (count > 10) return;
                if (!line.contains("%%") && !line.contains("&&")) {
                    count++;
                    continue;
                }
                if (line.contains("%%")) {
                    line = line.substring("%%".length(), line.length());
                    line = getXmlNodeElement(examPaperTitleNode, line);
//                    Log.d("----------->>", line);
                    fileWriter.write(line+"\n");
                    continue;
                }
                if (line.contains("&&")) {
                    line = line.substring("&&".length(), line.length());
                    line = getXmlNodeElement(examPaperAuthorNode, line);
//                    Log.d("----------->>", line);
                    fileWriter.write(line+"\n");
                    continue;
                }
            }
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                }
            }
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                }
            }
        }
    }

    private void getQuestions() {
        int id = 1;
        int currentQuestionType = 0;
        boolean inQuestionField = false;
        FileReader fileReader = null;
        FileWriter fileWriter = null;
        BufferedReader br;
        try {
            String line = null;
            StringBuilder tmp = new StringBuilder();
            StringBuilder backup = new StringBuilder();
            //打开输入流
            fileReader = new FileReader(txtFile);
            br = new BufferedReader(fileReader);
            //打开输出流
            fileWriter = new FileWriter(xmlFile,true);
            while ((line = br.readLine()) != null) {
//                Log.d("----------->>", line);
                if (line.contains("%%") || line.contains("&&")) {
                    id = 1;
                    inQuestionField = false;
                    tmp.delete(0, tmp.length());
                    continue;
                }
                if (line.contains("**")){
                    if (!(tmp.toString().equals(""))) {
                        String question = getQuestion(currentQuestionType, new RawQuestion(tmp.toString(), id, currentQuestionType));
                        fileWriter.write(question+"\n");
                    }
                    id = 1;
                    inQuestionField = false;
                    tmp.delete(0, tmp.length());
                    currentQuestionType = getQuestionType(line);
                    continue;
                }
                if (line.contains("##")) {
                    if (!(tmp.toString().equals(""))) {
                        String question = getQuestion(currentQuestionType, new RawQuestion(tmp.toString(), id, currentQuestionType));
                        fileWriter.write(question+"\n");
                        id++;
                    }
                    inQuestionField = true;
                    tmp.delete(0, tmp.length());
                    tmp.append(line.substring(2, line.length()) + "\n");
                    continue;
                }
                if (inQuestionField) {
                    tmp.append(line + "\n");
                }
            }
            if (!(tmp.toString().equals(""))) {
                String question = getQuestion(currentQuestionType, new RawQuestion(tmp.toString(), id, currentQuestionType));
                fileWriter.write(question+"\n"+"</"+examPaperNode+">");
            }
            fileReader.close();
            fileWriter.close();
        } catch (Exception e) {

        }
    }

    //获得题目类型
    private int getQuestionType(String line) {
        if (line.contains("填空")) return FILLINQUESTION;
        if (line.contains("判断")) return TFQUESTION;
        if (line.contains("单") && line.contains("选")) return SGLCHOQUESTION;
        if (line.contains("多") && line.contains("选")) return MULTCHOQUESTION;
        if (line.contains("简答")) return DISCUSSQUESTION;
        return 0;
    }

    private String getQuestion(int questionType, RawQuestion question) {
        switch (questionType) {
            case FILLINQUESTION:
                return getFillInQuestion(question);
            case TFQUESTION:
                return getTfQuestion(question);
            case SGLCHOQUESTION:
                return getSglChoQuestion(question);
            case MULTCHOQUESTION:
                return getMultChoQuestion(question);
            case DISCUSSQUESTION:
                return getDiscussQuestion(question);
        }
        return null;
    }

    private String getFillInQuestion(RawQuestion rawQuestion) {
        if (rawQuestion == null) return null;
        String[] parts = rawQuestion.question.split("\n");
        String[] elements = new String[parts.length + 2];
        elements[0] = getXmlNodeElement(idNode, "" + rawQuestion.id);
        elements[1] = getXmlNodeElement(typeNode, fillInQuestionNode);
        elements[2] = getXmlNodeElement(bodyNode, parts[0]);
        elements[3] = getXmlNodeElement(answerNode, parts[1]);
        return getXmlQuestionElement(rawQuestion.questionType, elements);
    }

    private String getTfQuestion(RawQuestion rawQuestion) {
        if (rawQuestion == null) return null;
        String[] parts = rawQuestion.question.split("\n");
        String[] elements = new String[parts.length + 2];
        elements[0] = getXmlNodeElement(idNode, "" + rawQuestion.id);
        elements[1] = getXmlNodeElement(typeNode, tfQuestionNodeNode);
        elements[2] = getXmlNodeElement(bodyNode, parts[0]);
        elements[3] = getXmlNodeElement(answerNode, parts[1]);
        return getXmlQuestionElement(rawQuestion.questionType, elements);
    }

    private String getSglChoQuestion(RawQuestion rawQuestion) {
        if (rawQuestion == null) return null;
        String[] parts = rawQuestion.question.split("\n");
        String[] elements = new String[parts.length+2];
        elements[0] = getXmlNodeElement(idNode,""+rawQuestion.id);
        elements[1] = getXmlNodeElement(typeNode, sglChoQuestionNode);
        elements[2] = getXmlNodeElement(bodyNode, parts[0]);
        elements[3] = getXmlNodeElement(answerNode, parts[1]);
        for (int i = 4;i<elements.length;i++){
            elements[i] = getXmlNodeElement(optionNode,parts[i-2]);
        }
        return getXmlQuestionElement(rawQuestion.questionType, elements);
    }

    private String getMultChoQuestion(RawQuestion rawQuestion) {
        if (rawQuestion == null) return null;
        String[] parts = rawQuestion.question.split("\n");
        String[] elements = new String[parts.length+2];
        elements[0] = getXmlNodeElement(idNode,""+rawQuestion.id);
        elements[1] = getXmlNodeElement(typeNode, sglChoQuestionNode);
        elements[2] = getXmlNodeElement(bodyNode, parts[0]);
        elements[3] = getXmlNodeElement(answerNode, parts[1]);
        for (int i = 4;i<elements.length;i++){
            elements[i] = getXmlNodeElement(optionNode,parts[i-2]);
        }
        return getXmlQuestionElement(rawQuestion.questionType, elements);
    }

    private String getDiscussQuestion(RawQuestion rawQuestion) {
        if (rawQuestion == null) return null;
        String[] parts = rawQuestion.question.split("\n");
        String[] elements = new String[parts.length + 2];
        elements[0] = getXmlNodeElement(idNode, "" + rawQuestion.id);
        elements[1] = getXmlNodeElement(typeNode, discussQuestionNode);
        elements[2] = getXmlNodeElement(bodyNode, parts[0]);
        elements[3] = getXmlNodeElement(answerNode, parts[1]);
        return getXmlQuestionElement(rawQuestion.questionType, elements);
    }


    private String getXmlNodeElement(String node, String value) {
        if (node == null || value == null) return null;
        return "<" + node + ">" + value + "</" + node + ">";
    }

    private String getNodeNameByQuestionType(int questionType) {
        switch (questionType) {
            case FILLINQUESTION:
                return fillInQuestionNode;
            case TFQUESTION:
                return tfQuestionNodeNode;
            case SGLCHOQUESTION:
                return sglChoQuestionNode;
            case MULTCHOQUESTION:
                return multChoQuestionNode;
            case DISCUSSQUESTION:
                return discussQuestionNode;
        }
        return null;
    }

    private String getXmlQuestionElement(int questionType, String[] elements) {
        String questionNode = getNodeNameByQuestionType(questionType);
        StringBuilder result = new StringBuilder();
        result.append("<" + questionNode + ">" + "\n");
        for (int i = 0; i < elements.length; i++) {
            result.append(elements[i] + "\n");
        }
        result.append("</" + questionNode + ">");
        return result.toString();
    }

    class RawQuestion {
        public int questionType;
        public String question;
        public int id;

        public RawQuestion(String question, int id, int questionType) {
            this.question = question;
            this.id = id;
            this.questionType = questionType;
        }
    }
}
