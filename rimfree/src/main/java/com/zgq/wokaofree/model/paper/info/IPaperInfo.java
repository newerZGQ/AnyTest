package com.zgq.wokaofree.model.paper.info;

import com.zgq.wokaofree.model.paper.QuestionType;
import com.zgq.wokaofree.model.schedule.ISchedule;

import java.util.ArrayList;

import io.realm.RealmList;

/**
 * Created by zgq on 2017/2/28.
 */

public interface IPaperInfo {
    String getId();

    ISchedule getSchedule();

    void setInSchedule(boolean inInSchedule);

    String getTitle();

    void setTitle(String title);

    String getAuthor();

    void setAuthor(String author);

    String getCreateDate();

    void setCreateDate(String createDate);

    String getLastStudyDate();

    void setLastStudyDate(String lastStudyDate);

    boolean isStared();

    void setStared(boolean stared);

    boolean isParseRight();

    void setParseRight(boolean parseRight);

    int getStudyCount();

    void setStudyCount(int studyCount);

    boolean isInSchedule();

    ArrayList<QuestionType> getQuestionTypes();

    void addQuestionType(QuestionType type);

}
