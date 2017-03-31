package com.zgq.wokao.ui.adapter;

import android.view.View;
import android.widget.Button;

import com.wirelesspienetwork.overview.model.ViewHolder;
import com.zgq.wokao.R;
import com.zgq.wokao.model.viewdate.QstData;

/**
 * Created by zgq on 2017/3/31.
 */

public class CardViewHolder extends ViewHolder {
    public View view;
//    public Button btn;
    public QstData model;
    public CardViewHolder(View view) {
        super(view);
        this.view = view;
//        this.btn = (Button)view.findViewById(R.id.test_btn);
    }
}
