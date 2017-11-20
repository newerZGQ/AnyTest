package com.zgq.wokao.entity.paper.info;

import com.zgq.wokao.entity.CascadeDeleteable;

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
