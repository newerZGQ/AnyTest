package com.zgq.wokaofree.ui.adapter;

import android.view.View;

/**
 * Created by zgq on 16-7-6.
 */
public interface BaseStudySystemAdapter {
    View getCurrentView();

    int getCurrentPosition();

    boolean showCurrentAnswer();

    void hideCurrentAnswer();

    void setPaperId(String paperId);

    String getPaperId();
}
