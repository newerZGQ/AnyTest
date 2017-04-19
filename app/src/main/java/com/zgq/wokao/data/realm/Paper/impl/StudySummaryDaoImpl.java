package com.zgq.wokao.data.realm.Paper.impl;

import com.zgq.wokao.data.realm.BaseRealmProvider;
import com.zgq.wokao.data.realm.Paper.IStudySummaryDao;
import com.zgq.wokao.model.total.IStudySummary;
import com.zgq.wokao.model.total.StudySummary;
import com.zgq.wokao.model.total.TotalDailyCount;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by zhangguoqiang on 2017/4/9.
 */

public class StudySummaryDaoImpl extends BaseRealmProvider<StudySummary> implements IStudySummaryDao {
    Realm realm = Realm.getDefaultInstance();
    private StudySummaryDaoImpl(){
        setClass(StudySummary.class);
    }

    public static StudySummaryDaoImpl getInstance(){
        return InstanceHolder.instance;
    }

    public static class InstanceHolder{
        public static StudySummaryDaoImpl instance = new StudySummaryDaoImpl();
    }
    //override delete避免被删除
    @Override
    public void delete(StudySummary entity) {

    }

    @Override
    public void addStudySummary(StudySummary studySummary) {
        if (getStudySummary() == null) {
            super.save(studySummary);
        }
    }

    @Override
    public void updateSummary(StudySummary studySummary, boolean correct) {
        realm.beginTransaction();
        studySummary.studyCountPlus_1();
        studySummary.updateDailyCount();
        if (correct){
            studySummary.correctCountPlus_1();
        }
        realm.commitTransaction();
    }

    @Override
    public StudySummary getStudySummary() {
        RealmResults<StudySummary> results = realm
                .where(StudySummary.class)
                .findAll();
        if (results.size() == 0)
            return null;
        return results.first();
    }
}
