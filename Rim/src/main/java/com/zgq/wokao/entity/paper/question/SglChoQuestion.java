package com.zgq.wokao.entity.paper.question;

import com.zgq.wokao.entity.CascadeDeleteable;
import com.zgq.wokao.entity.paper.question.answer.Answer;
import com.zgq.wokao.entity.paper.question.body.QuestionBody;
import com.zgq.wokao.entity.paper.question.info.QuestionInfo;
import com.zgq.wokao.entity.paper.question.option.Options;
import com.zgq.wokao.entity.paper.question.record.QuestionRecord;

import io.realm.RealmObject;
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
public class SglChoQuestion extends RealmObject implements CascadeDeleteable {

    private QuestionBody body;
    private Answer answer;
    private QuestionInfo info;
    private QuestionRecord record;
    private Options options;

    @Override
    public void cascadeDelete() {
        body.cascadeDelete();
        info.cascadeDelete();
        answer.cascadeDelete();
        record.cascadeDelete();
        deleteFromRealm();
    }
}
