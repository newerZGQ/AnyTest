package com.zgq.wokao.data.realm.Paper.impl;

import com.zgq.wokao.data.realm.BaseRealmProvider;
import com.zgq.wokao.model.total.IStudySummary;
import com.zgq.wokao.model.total.StudySummary;
import com.zgq.wokao.model.total.TotalDailyCount;

/**
 * Created by zhangguoqiang on 2017/4/9.
 */

public class StudySummaryDaoImpl extends BaseRealmProvider<StudySummary> implements IStudySummary {
    private StudySummaryDaoImpl(){}
    @Override
    public void studyCountPlus_1() {

    }

    @Override
    public void correctCountPlus_1() {

    }

    @Override
    public void updateDailyCount() {

    }

    @Override
    public TotalDailyCount getCurrentDailyCount() {
        return null;
    }

    //override delete避免被删除
    @Override
    public void delete(StudySummary entity) {

    }
}
