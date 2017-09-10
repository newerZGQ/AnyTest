package com.zgq.wokao.model.paper;

import com.zgq.wokao.model.paper.info.IPaperInfo;
import com.zgq.wokao.model.paper.question.impl.DiscussQuestion;
import com.zgq.wokao.model.paper.question.impl.FillInQuestion;
import com.zgq.wokao.model.paper.question.impl.MultChoQuestion;
import com.zgq.wokao.model.paper.question.impl.SglChoQuestion;
import com.zgq.wokao.model.paper.question.impl.TFQuestion;
import com.zgq.wokao.model.search.Searchable;

import io.realm.RealmList;

/**
 * Created by zgq on 16-6-18.
 */
public interface IExamPaper extends Searchable {
    public IPaperInfo getPaperInfo();

    public RealmList<FillInQuestion> getFillInQuestions();

    public RealmList<TFQuestion> getTfQuestions();

    public RealmList<SglChoQuestion> getSglChoQuestions();

    public RealmList<MultChoQuestion> getMultChoQuestions();

    public RealmList<DiscussQuestion> getDiscussQuestions();
}
