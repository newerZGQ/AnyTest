package com.zgq.wokao.ui.presenter;

import com.zgq.wokao.exception.ParseException;
import com.zgq.wokao.model.paper.IExamPaper;
import com.zgq.wokao.model.schedule.DailyRecord;
import com.zgq.wokao.model.total.TotalDailyCount;

import java.util.List;

/**
 * Created by zgq on 2017/3/4.
 */

public interface IHomePrerenter {
    List<TotalDailyCount> getDailyRecords();
}
