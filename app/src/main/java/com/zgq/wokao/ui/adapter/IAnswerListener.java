package com.zgq.wokao.ui.adapter;

/**
 * Created by zgq on 2017/4/19.
 */

public interface IAnswerListener {
    void onCorrect(String questionId);

    void onFailed(String questionId);
}
