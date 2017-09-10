package com.zgq.wokao.model.paper.info;

import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.schedule.ISchedule;

import java.util.ArrayList;

import io.realm.RealmList;

/**
 * Created by zgq on 2017/2/28.
 */

public interface IPaperInfo {
    public String getId();

    public ISchedule getSchedule();

    public void setInSchedule(boolean inInSchedule);

    public String getTitle();

    public void setTitle(String title);

    public String getAuthor();

    public void setAuthor(String author);

    public String getCreateDate();

    public void setCreateDate(String createDate);

    public String getLastStudyDate();

    public void setLastStudyDate(String lastStudyDate);

    public boolean isStared();

    public void setStared(boolean stared);

    public boolean isParseRight();

    public void setParseRight(boolean parseRight);

    public int getStudyCount();

    public void setStudyCount(int studyCount);

    public boolean isInSchedule();

    public ArrayList<QuestionType> getQuestionTypes();

    public void addQuestionType(QuestionType type);

}
