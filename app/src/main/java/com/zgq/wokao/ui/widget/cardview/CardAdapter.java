package com.zgq.wokao.ui.widget.cardview;

/**
 * Created by zgq on 2017/5/29.
 */

import android.support.v7.widget.CardView;

public interface CardAdapter {

    int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}

