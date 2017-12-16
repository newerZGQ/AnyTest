package com.zgq.wokao.adapter;

import android.view.View;

public interface StudySystemAdapter {
    View getCurrentView();

    int getCurrentPosition();

    boolean showCurrentAnswer();

    void hideCurrentAnswer();
}
