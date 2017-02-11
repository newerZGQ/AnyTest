package com.zgq.wokao.model.search;

import com.zgq.wokao.model.paper.ExamPaperInfo;
import com.zgq.wokao.model.paper.Question;

/**
 * Created by zgq on 2017/2/11.
 */

public class SearchQstItem implements Searchable {
    private ExamPaperInfo info;
    private int qstType;
    private int qstId;
    private Question qst;

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

    public Question getQst() {
        return qst;
    }

    public void setQst(Question qst) {
        this.qst = qst;
    }
}
