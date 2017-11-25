package com.zgq.wokao.entity.paper;

import com.orhanobut.logger.Logger;
import com.zgq.wokao.entity.CascadeDeleteable;
import com.zgq.wokao.entity.paper.info.ExamPaperInfo;
import com.zgq.wokao.entity.paper.question.DiscussQuestion;
import com.zgq.wokao.entity.paper.question.FillInQuestion;
import com.zgq.wokao.entity.paper.question.MultChoQuestion;
import com.zgq.wokao.entity.paper.question.SglChoQuestion;
import com.zgq.wokao.entity.paper.question.TFQuestion;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NormalExamPaper extends RealmObject implements CascadeDeleteable {
    @PrimaryKey
    private String id;

    private ExamPaperInfo paperInfo;
    private RealmList<FillInQuestion> fillInQuestions;
    private RealmList<TFQuestion> tfQuestions;
    private RealmList<SglChoQuestion> sglChoQuestions;
    private RealmList<MultChoQuestion> multChoQuestions;
    private RealmList<DiscussQuestion> discussQuestions;

    @Override
    public void cascadeDelete() {
        if (paperInfo != null) {
            paperInfo.cascadeDelete();
        }
        for (int i = 0; i < tfQuestions.size(); i++) {
            tfQuestions.get(i).cascadeDelete();
        }
        for (int i = 0; i < fillInQuestions.size(); i++) {
            fillInQuestions.get(i).cascadeDelete();
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
