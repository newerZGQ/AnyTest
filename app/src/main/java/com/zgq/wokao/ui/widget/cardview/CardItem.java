package com.zgq.wokao.ui.widget.cardview;

import com.zgq.cardview.CardView;

/**
 * Created by zgq on 2017/5/29.
 */

public class CardItem {

    private String mText;
    private String mTitle;

    public CardItem(String title, String text) {
        mTitle = title;
        mText = text;
    }

    public String getText() {
        return mText;
    }

    public String getTitle() {
        return mTitle;
    }
}
