package com.zgq.wokao.entity.paper.question.info;

import com.zgq.wokao.entity.CascadeDeleteable;

import io.realm.RealmObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by zgq on 2017/2/27.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class QuestionInfo extends RealmObject implements CascadeDeleteable {

    private String id;
    private String type;
    private int qstId;
    private boolean isStared;

    @Override
    public void cascadeDelete() {
        deleteFromRealm();
    }
}
