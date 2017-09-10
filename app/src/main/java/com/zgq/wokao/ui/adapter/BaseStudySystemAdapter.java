package com.zgq.wokao.ui.adapter;

import android.view.View;

/**
 * Created by zgq on 16-7-6.
 */
public interface BaseStudySystemAdapter {
    public abstract View getCurrentView();

    public abstract int getCurrentPosition();

    public abstract boolean showCurrentAnswer();

    public abstract void hideCurrentAnswer();

    public abstract void setPaperId(String paperId);

    public abstract String getPaperId();
}
