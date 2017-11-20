package com.zgq.wokao.entity.summary;

import io.realm.RealmList;
import io.realm.RealmObject;
import lombok.Data;

@Data
public class StudySummary extends RealmObject {
    private int studyCount;
    private int correctCount;
    private RealmList<TotalDailyCount> lastWeekRecords;
}
