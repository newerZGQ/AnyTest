package com.zgq.wokao.model.search;

import com.zgq.wokao.model.paper.info.ExamIPaperInfo;

/**
 * Created by zgq on 2017/2/11.
 */

public class SearchInfoItem implements Searchable {
    private ExamIPaperInfo info;

    public ExamIPaperInfo getInfo() {
        return info;
    }

    public void setInfo(ExamIPaperInfo info) {
        this.info = info;
    }
}
