package com.zgq.wokao.model.paper;

import com.zgq.wokao.model.paper.info.IPaperInfo;
import com.zgq.wokao.model.schedule.ISchedule;
import com.zgq.wokao.model.search.Searchable;

/**
 * Created by zgq on 16-6-18.
 */
public interface IExamPaper extends Searchable {
    public IPaperInfo getPaperInfo();
}
