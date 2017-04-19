package com.zgq.wokao.ui.adapter;

/**
 * Created by zgq on 2017/4/19.
 */

public interface IAnswerListener {
    public void onCorrect(String questionId);
    public void onFailed(String questionId);
}
