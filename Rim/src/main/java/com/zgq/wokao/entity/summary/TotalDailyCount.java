package com.zgq.wokao.entity.summary;

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
public class TotalDailyCount extends RealmObject {
    @PrimaryKey
    private String id;
    private int dailyCount;
    private String date;
}
