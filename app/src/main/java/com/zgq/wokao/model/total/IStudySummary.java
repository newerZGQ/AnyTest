package com.zgq.wokao.model.total;

/**
 * Created by zhangguoqiang on 2017/4/9.
 */

public interface IStudySummary {

    void initSummary();

    void updateSummary(boolean isCorrect);

    int getStudyCount();

    int getCorrectCount();
}
