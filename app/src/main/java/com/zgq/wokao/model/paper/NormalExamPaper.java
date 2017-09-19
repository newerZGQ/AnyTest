package com.zgq.wokao.model.paper;

import com.zgq.wokao.model.CascadeDeleteable;
import com.zgq.wokao.model.paper.info.ExamPaperInfo;
import com.zgq.wokao.model.paper.question.IQuestion;
import com.zgq.wokao.model.paper.question.impl.DiscussQuestion;
import com.zgq.wokao.model.paper.question.impl.FillInQuestion;
import com.zgq.wokao.model.paper.question.impl.MultChoQuestion;
import com.zgq.wokao.model.paper.question.impl.SglChoQuestion;
import com.zgq.wokao.model.paper.question.impl.TFQuestion;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by zgq on 16-6-18.
 */
public class NormalExamPaper extends RealmObject implements IExamPaper, CascadeDeleteable {
    private ExamPaperInfo paperInfo = new ExamPaperInfo();
    private RealmList<FillInQuestion> fillInQuestions = new RealmList<>();
    private RealmList<TFQuestion> tfQuestions = new RealmList<>();
    private RealmList<SglChoQuestion> sglChoQuestions = new RealmList<>();
    private RealmList<MultChoQuestion> multChoQuestions = new RealmList<>();
    private RealmList<DiscussQuestion> discussQuestions = new RealmList<>();

    public NormalExamPaper() {
    }

    @Override
    public ExamPaperInfo getPaperInfo() {
        return paperInfo;
    }

    public void setPaperInfo(ExamPaperInfo paperInfo) {
        this.paperInfo = paperInfo;
    }

    public ArrayList<IQuestion> getAllQuestions() {
        return null;
    }


    public RealmList<FillInQuestion> getFillInQuestions() {
        return fillInQuestions;
    }

    public void setFillInQuestions(RealmList<FillInQuestion> fillInQuestions) {
        this.fillInQuestions = fillInQuestions;
    }

    public RealmList<TFQuestion> getTfQuestions() {
        return tfQuestions;
    }

    public void setTfQuestions(RealmList<TFQuestion> tfQuestions) {
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

    public RealmList<DiscussQuestion> getDiscussQuestions() {
        return discussQuestions;
    }

    public void setDiscussQuestions(RealmList<DiscussQuestion> discussQuestions) {
        this.discussQuestions = discussQuestions;
    }

    public int getQuestionsCount() {
        return getFillInQuestions().size() + getTfQuestions().size()
                + getSglChoQuestions().size() + getMultChoQuestions().size()
                + getDiscussQuestions().size();
    }

    @Override
    public void cascadeDelete() {
        if (paperInfo != null) {
            paperInfo.cascadeDelete();
        }
        for (int i = 0; i < fillInQuestions.size(); i++) {
            fillInQuestions.get(i).cascadeDelete();
        }
        for (int i = 0; i < tfQuestions.size(); i++) {
            tfQuestions.get(i).cascadeDelete();
        }
        for (int i = 0; i < sglChoQuestions.size(); i++) {
            sglChoQuestions.get(i).cascadeDelete();
        }
        for (int i = 0; i < multChoQuestions.size(); i++) {
            multChoQuestions.get(i).cascadeDelete();
        }
        for (int i = 0; i < discussQuestions.size(); i++) {
            discussQuestions.get(i).cascadeDelete();
        }
        deleteFromRealm();
    }

    @Override
    public String toString() {
        return "试卷：" + paperInfo.getTitle() + "\n" + "  " + "作者" + paperInfo.getAuthor() + " id:" + paperInfo.getId();
    }
}
