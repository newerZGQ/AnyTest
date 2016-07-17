package com.zgq.wokao.data;

/**
 * Created by zgq on 16-7-17.
 */
public class MyQuestionAnswer implements QuestionAnswer {
    private String answer;
    @Override
    public boolean hasAnswer() {
        return false;
    }

    @Override
    public void setAnswer(String s) {
        this.answer = s;
    }

    @Override
    public String getAnswer() {
        return answer;
    }
}
