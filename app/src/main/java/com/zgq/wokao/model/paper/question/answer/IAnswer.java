package com.zgq.wokao.model.paper.question.answer;

/**
 * Created by zgq on 2017/2/27.
 */

public interface IAnswer {
    public String getContent();
    public void setContent(String content);
    public abstract boolean hasAnswer();
}
