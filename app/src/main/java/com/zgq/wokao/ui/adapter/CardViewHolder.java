package com.zgq.wokao.ui.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wirelesspienetwork.overview.model.ViewHolder;
import com.zgq.cardview.CardView;
import com.zgq.wokao.R;
import com.zgq.wokao.model.viewdate.QstData;

/**
 * Created by zgq on 2017/3/31.
 */

public class CardViewHolder extends ViewHolder {
    public View view;
    public CardView cardView;
//    public TextView title;
//    public TextView accuracy;
//    public TextView accuracyTv;
//    public TextView fallables;
//    public TextView qstInfo;
//    public Button fallable_1;
//    public Button fallable_2;
//    public Button fallable_3;

    public CardViewHolder(View view) {
        super(view);
        this.view = view;
        cardView = (CardView) view.findViewById(R.id.cardview);
//        title = (TextView) view.findViewById(R.id.qst_title);
//        accuracy = (TextView) view.findViewById(R.id.qst_accuracy);
//        accuracyTv = (TextView) view.findViewById(R.id.qst_accuracy_tv);
//        fallables = (TextView)view.findViewById(R.id.qst_fallable);
//        qstInfo = (TextView) view.findViewById(R.id.qst_info);
//        fallable_1 = (Button) view.findViewById(R.id.fallable_1);
//        fallable_2 = (Button) view.findViewById(R.id.fallable_2);
//        fallable_3 = (Button) view.findViewById(R.id.fallable_3);
    }
}
