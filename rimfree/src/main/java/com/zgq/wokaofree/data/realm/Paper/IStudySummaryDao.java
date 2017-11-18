package com.zgq.wokaofree.data.realm.Paper;

import com.zgq.wokaofree.model.total.StudySummary;

/**
 * Created by zhangguoqiang on 2017/4/9.
 */

public interface IStudySummaryDao {

    void initStudySummary(StudySummary studySummary);

    void addStudySummary(StudySummary studySummary);

    //更新学习记录，correct是表示记录的答题是否回答正确
    void updateSummary(StudySummary studySummary, boolean correct);

    StudySummary getStudySummary();
}
