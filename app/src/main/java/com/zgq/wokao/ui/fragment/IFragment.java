package com.zgq.wokao.ui.fragment;

import android.content.Context;
import android.view.LayoutInflater;

/**
 * Created by zgq on 2017/2/24.
 */

public interface IFragment {
    void setContext(Context context);

    LayoutInflater getInflater();
}
