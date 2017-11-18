package com.zgq.wokaofree.ui.presenter;

import com.zgq.wokaofree.model.paper.QuestionType;
import com.zgq.wokaofree.model.viewdate.ScheduleData;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/3/5.
 */

public interface ISchedulePresenter {
    ArrayList<ScheduleData> getScheduleDatas();

    void updateDatas();

    ScheduleData getScheduleInfo(int position);

    int getPaperCount();

    void setDailyCount(int position, int count);

    QuestionType getLastStudyType(String paperId);

    int getLastStudyPos(String paperId);
}