package com.zgq.wokaofree.model.search;

import com.zgq.wokaofree.model.paper.info.ExamPaperInfo;

/**
 * Created by zgq on 2017/2/11.
 */

public class SearchInfoItem implements Searchable {
    private ExamPaperInfo info;

    public ExamPaperInfo getInfo() {
        return info;
    }

    public void setInfo(ExamPaperInfo info) {
        this.info = info;
    }
}
