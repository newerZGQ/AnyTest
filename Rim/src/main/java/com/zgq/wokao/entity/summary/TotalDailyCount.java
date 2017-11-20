package com.zgq.wokao.entity.summary;

import io.realm.RealmObject;
import lombok.Data;

/**
 * Created by zhangguoqiang on 2017/4/9.
 */

@Data
public class TotalDailyCount extends RealmObject {
    private int dailyCount;
    private String date;
}
