package com.zgq.wokao.model.paper.question.answer;

/**
 * Created by zgq on 16-7-17.
 */
public class MyAnswer implements IAnswer {
    private String content;

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean hasAnswer() {
        if (content == null || content.equals("")) {
            return false;
        }
        return true;
    }
}
