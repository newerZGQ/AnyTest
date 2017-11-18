package com.zgq.wokaofree.ui.presenter;

import com.zgq.wokaofree.exception.ParseException;
import com.zgq.wokaofree.model.paper.IExamPaper;
import com.zgq.wokaofree.model.schedule.DailyRecord;
import com.zgq.wokaofree.model.total.TotalDailyCount;

import java.util.List;

/**
 * Created by zgq on 2017/3/4.
 */

public interface IHomePrerenter {
    List<TotalDailyCount> getDailyRecords();
}
