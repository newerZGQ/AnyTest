package com.zgq.wokao.action.paper;

import com.zgq.wokao.model.total.StudySummary;

/**
 * Created by zhangguoqiang on 2017/4/9.
 */

public interface IStudySummaryAction {
    public void addStudySummary(StudySummary studySummary);
    //更新学习记录，correct是表示记录的答题是否回答正确
    public void updateSummary(StudySummary studySummary,boolean correct);
    public StudySummary getStudySummary();
}