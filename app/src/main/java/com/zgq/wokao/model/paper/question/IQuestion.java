package com.zgq.wokao.model.paper.question;

import com.zgq.wokao.model.paper.question.answer.Answer;
import com.zgq.wokao.model.paper.question.body.QuestionBody;
import com.zgq.wokao.model.paper.question.info.QuestionInfo;
import com.zgq.wokao.model.paper.question.option.Options;
import com.zgq.wokao.model.paper.question.record.QuestionRecord;

/**
 * Created by zgq on 16-6-18.
 */
public interface IQuestion {
    QuestionBody getBody();

    QuestionInfo getInfo();

    Answer getAnswer();

    Options getOptions();

    QuestionRecord getRecord();

}
