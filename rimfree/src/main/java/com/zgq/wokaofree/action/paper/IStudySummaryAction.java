package com.zgq.wokaofree.action.paper;

import com.zgq.wokaofree.model.paper.QuestionType;
import com.zgq.wokaofree.model.total.StudySummary;

/**
 * Created by zhangguoqiang on 2017/4/9.
 */

public interface IStudySummaryAction {

    void initStudySummary();

    //更新学习记录，correct是表示记录的答题是否回答正确
    void updateSummary(QuestionType type, boolean correct);

    StudySummary getStudySummary();
}
