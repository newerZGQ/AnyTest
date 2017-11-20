package com.zgq.wokao.entity.summary;

import io.realm.RealmList;
import io.realm.RealmObject;
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
    private int studyCount;
    private int correctCount;
    private RealmList<TotalDailyCount> lastWeekRecords;
}
