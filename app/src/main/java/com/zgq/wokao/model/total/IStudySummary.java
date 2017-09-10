package com.zgq.wokao.model.total;

/**
 * Created by zhangguoqiang on 2017/4/9.
 */

public interface IStudySummary {
    void studyCountPlus_1();

    void correctCountPlus_1();

    void updateDailyCount();

    TotalDailyCount getCurrentDailyCount();
}
