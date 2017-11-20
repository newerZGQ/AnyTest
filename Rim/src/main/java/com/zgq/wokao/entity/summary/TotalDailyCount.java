package com.zgq.wokao.entity.summary;

import io.realm.RealmObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by zhangguoqiang on 2017/4/9.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TotalDailyCount extends RealmObject {
    private int dailyCount;
    private String date;
}
