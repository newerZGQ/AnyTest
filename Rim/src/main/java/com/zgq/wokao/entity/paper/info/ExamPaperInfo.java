package com.zgq.wokao.entity.paper.info;

import com.zgq.wokao.entity.CascadeDeleteable;

import java.io.Serializable;

import io.realm.RealmObject;
import lombok.Data;

/**
 * Created by zgq on 16-6-20.
 */

@Data
public class ExamPaperInfo extends RealmObject implements Serializable, CascadeDeleteable {
    private String id;
    private String title;
    private String author;
    private String createDate;
    private boolean stared;
    private Schedule schedule;

    @Override
    public void cascadeDelete() {
        schedule.cascadeDelete();
        deleteFromRealm();
    }
}
