package com.zgq.wokao.model.paper.info;

import com.zgq.wokao.model.schedule.ISchedule;

/**
 * Created by zgq on 2017/2/28.
 */

public interface IPaperInfo {
    public void setStared(boolean stared);
    public ISchedule getSchedule();
    public void setInSchedule(boolean inInSchedule);
}
