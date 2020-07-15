package com.zgq.wokao.module.home.summary;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StudySummary extends RealmObject {
    @PrimaryKey
    private String id;
    private int studyCount;
    private int correctCount;
    private RealmList<TotalDailyCount> lastWeekRecords;
}
