package com.zgq.wokao.adapter;

import android.view.View;

/**
 * Created by zgq on 16-7-6.
 */
public interface BaseStudySystemAdapter {
    View getCurrentView();

    int getCurrentPosition();

    boolean showCurrentAnswer();

    void hideCurrentAnswer();
}
