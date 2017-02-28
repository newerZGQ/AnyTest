package com.zgq.wokao.model.paper;

import com.zgq.wokao.model.paper.info.ExamPaperInfo;
import com.zgq.wokao.model.paper.question.IQuestion;
import com.zgq.wokao.model.paper.question.impl.DiscussIQuestion;
import com.zgq.wokao.model.paper.question.impl.FillInIQuestion;
import com.zgq.wokao.model.paper.question.impl.MultChoQuestion;
import com.zgq.wokao.model.paper.question.impl.SglChoQuestion;
import com.zgq.wokao.model.paper.question.impl.TFIQuestion;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by zgq on 16-6-18.
 */
public class NormalIExamPaper extends RealmObject implements IExamPaper {
    private ExamPaperInfo paperInfo = new ExamPaperInfo();
    private RealmList<FillInIQuestion> fillInQuestions;
    private RealmList<TFIQuestion> tfQuestions;
    private RealmList<SglChoQuestion> sglChoQuestions;
    private RealmList<MultChoQuestion> multChoQuestions;
    private RealmList<DiscussIQuestion> discussQuestions;

    public NormalIExamPaper(){}

    public NormalIExamPaper(ExamPaperInfo paperInfo, RealmList<FillInIQuestion> fillInQuestions,
                            RealmList<TFIQuestion> tfQuestions, RealmList<SglChoQuestion> sglChoQuestions,
                            RealmList<MultChoQuestion> multChoQuestions,
                            RealmList<DiscussIQuestion> discussQuestions) {
        this.paperInfo = paperInfo;
        this.fillInQuestions = fillInQuestions;
        this.tfQuestions = tfQuestions;
        this.sglChoQuestions = sglChoQuestions;
        this.multChoQuestions = multChoQuestions;
        this.discussQuestions = discussQuestions;
    }

    public ExamPaperInfo getPaperInfo() {
        return paperInfo;
    }

    public void setPaperInfo(ExamPaperInfo paperInfo) {
        this.paperInfo = paperInfo;
    }

    public ArrayList<IQuestion> getAllQuestions() {
        return null;
    }


    public RealmList<FillInIQuestion> getFillInQuestions() {
        return fillInQuestions;
    }

    public void setFillInQuestions(RealmList<FillInIQuestion> fillInQuestions) {
        this.fillInQuestions = fillInQuestions;
    }

    public RealmList<TFIQuestion> getTfQuestions() {
        return tfQuestions;
    }

    public void setTfQuestions(RealmList<TFIQuestion> tfQuestions) {
        this.tfQuestions = tfQuestions;
    }

    public RealmList<SglChoQuestion> getSglChoQuestions() {
        return sglChoQuestions;
    }

    public void setSglChoQuestions(RealmList<SglChoQuestion> sglChoQuestions) {
        this.sglChoQuestions = sglChoQuestions;
    }

    public RealmList<MultChoQuestion> getMultChoQuestions() {
        return multChoQuestions;
    }

    public void setMultChoQuestions(RealmList<MultChoQuestion> multChoQuestions) {
        this.multChoQuestions = multChoQuestions;
    }

    public RealmList<DiscussIQuestion> getDiscussQuestions() {
        return discussQuestions;
    }

    public void setDiscussQuestions(RealmList<DiscussIQuestion> discussQuestions) {
        this.discussQuestions = discussQuestions;
    }

    public int getQuestionsCount(){
        return getFillInQuestions().size()+getTfQuestions().size()+getSglChoQuestions().size()+getMultChoQuestions().size()+getDiscussQuestions().size();
    }

    @Override
    public String toString() {
        return "试卷："+paperInfo.getTitle()+"\n"+"  "+"作者"+paperInfo.getAuthor();
    }
}
