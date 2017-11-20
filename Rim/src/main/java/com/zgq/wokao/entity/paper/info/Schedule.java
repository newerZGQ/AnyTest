package com.zgq.wokao.entity.paper.info;

import com.zgq.wokao.entity.CascadeDeleteable;

import io.realm.RealmList;
import io.realm.RealmObject;
import lombok.Data;

@Data
public class Schedule extends RealmObject implements CascadeDeleteable {

    private boolean isInSked;

    private int dailyTask;

    private String lastStudyType;
    private int lastStudyNum;

    private RealmList<DailyRecord> dailyRecords;

    @Override
    public void cascadeDelete() {
        deleteFromRealm();
    }
}
