package com.zgq.wokao.model.total;

/**
 * Created by zhangguoqiang on 2017/4/9.
 */

public interface IStudySummary {
    public void studyCountPlus_1();
    public void correctCountPlus_1();
    public void updateDailyCount();
    public TotalDailyCount getCurrentDailyCount();
}
