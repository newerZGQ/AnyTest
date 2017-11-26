package com.zgq.wokao.entity.paper.question;

import com.zgq.wokao.entity.CascadeDeleteable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by zgq on 16-6-18.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DiscussQuestion extends RealmObject implements CascadeDeleteable, IQuestion {
    @PrimaryKey
    private String id;
    private QuestionBody body;
    private Answer answer;
    private QuestionInfo info;
    private QuestionRecord record;

    @Override
    public void cascadeDelete() {
        body.cascadeDelete();
        info.cascadeDelete();
        answer.cascadeDelete();
        record.cascadeDelete();
        deleteFromRealm();
    }
}
