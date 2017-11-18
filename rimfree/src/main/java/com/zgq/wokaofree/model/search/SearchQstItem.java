package com.zgq.wokaofree.model.search;

import com.zgq.wokaofree.model.paper.QuestionType;
import com.zgq.wokaofree.model.paper.info.ExamPaperInfo;
import com.zgq.wokaofree.model.paper.question.IQuestion;

/**
 * Created by zgq on 2017/2/11.
 */

public class SearchQstItem implements Searchable {
    private ExamPaperInfo info;
    private QuestionType qstType;
    private int qstId;
    private IQuestion qst;

    public ExamPaperInfo getInfo() {
        return info;
    }

    public void setInfo(ExamPaperInfo info) {
        this.info = info;
    }

    public int getQstId() {
        return qstId;
    }

    public void setQstId(int qstId) {
        this.qstId = qstId;
    }

    public QuestionType getQstType() {
        return qstType;
    }

    public void setQstType(QuestionType qstType) {
        this.qstType = qstType;
    }

    public IQuestion getQst() {
        return qst;
    }

    public void setQst(IQuestion qst) {
        this.qst = qst;
    }
}
