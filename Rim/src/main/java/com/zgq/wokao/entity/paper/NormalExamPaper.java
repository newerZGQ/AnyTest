package com.zgq.wokao.entity.paper;

import com.zgq.wokao.entity.CascadeDeleteable;
import com.zgq.wokao.entity.paper.info.ExamPaperInfo;
import com.zgq.wokao.entity.paper.question.DiscussQuestion;
import com.zgq.wokao.entity.paper.question.FillInQuestion;
import com.zgq.wokao.entity.paper.question.MultChoQuestion;
import com.zgq.wokao.entity.paper.question.SglChoQuestion;
import com.zgq.wokao.entity.paper.question.TFQuestion;

import io.realm.RealmList;
import io.realm.RealmObject;
import lombok.Data;

/**
 * Created by zgq on 16-6-18.
 */

@Data
public class NormalExamPaper extends RealmObject implements CascadeDeleteable {
    private ExamPaperInfo paperInfo = new ExamPaperInfo();
    private RealmList<FillInQuestion> fillInQuestions = new RealmList<>();
    private RealmList<TFQuestion> tfQuestions = new RealmList<>();
    private RealmList<SglChoQuestion> sglChoQuestions = new RealmList<>();
    private RealmList<MultChoQuestion> multChoQuestions = new RealmList<>();
    private RealmList<DiscussQuestion> discussQuestions = new RealmList<>();

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
}
