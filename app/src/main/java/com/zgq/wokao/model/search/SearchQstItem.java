package com.zgq.wokao.model.search;

import com.zgq.wokao.model.paper.ExamPaperInfo;
import com.zgq.wokao.model.paper.question.IQuestion;

/**
 * Created by zgq on 2017/2/11.
 */

public class SearchQstItem implements Searchable {
    private ExamPaperInfo info;
    private int qstType;
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

    public int getQstType() {
        return qstType;
    }

    public void setQstType(int qstType) {
        this.qstType = qstType;
    }

    public IQuestion getQst() {
        return qst;
    }

    public void setQst(IQuestion qst) {
        this.qst = qst;
    }
}
