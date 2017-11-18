package com.zgq.wokaofree.action.paper;

import com.zgq.wokaofree.model.paper.IExamPaper;
import com.zgq.wokaofree.model.paper.QuestionType;

/**
 * Created by zgq on 2017/2/28.
 */

public interface IPaperSchdlAction {
    void openSchedule(IExamPaper paper);

    void closeSchedule(IExamPaper paper);

    void setDailyCount(IExamPaper paper, int count);

    //每次答题后调用来更新学习记录
    void updateDailyRecord(IExamPaper paper);

    void updateLastStudyPosition(IExamPaper paper, QuestionType type, int position);

}
