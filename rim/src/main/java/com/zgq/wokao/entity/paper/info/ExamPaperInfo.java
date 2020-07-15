package com.zgq.wokao.entity.paper.info;

import com.zgq.wokao.entity.CascadeDeleteable;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ExamPaperInfo extends RealmObject implements Serializable, CascadeDeleteable {
    @PrimaryKey
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
