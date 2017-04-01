package com.zgq.wokao.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.wirelesspienetwork.overview.model.OverviewAdapter;
import com.zgq.wokao.R;
import com.zgq.wokao.Util.LogUtil;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.viewdate.QstData;
import com.zgq.wokao.ui.activity.AnswerStudyActivity;

import java.util.List;
import java.util.Random;

/**
 * Created by zgq on 2017/3/31.
 */

public class CardViewAdapter extends OverviewAdapter<CardViewHolder, QstData> {
    private static final String TAG = CardViewAdapter.class.getSimpleName();
    Random random = new Random();

    private int position = 0;

    private Context context;
    private List<QstData> qstDatas;
    public CardViewAdapter(List<QstData> qstDatas,Context context) {
        super(qstDatas);
        this.context = context;
        this.qstDatas = qstDatas;
    }

    @Override
    public CardViewHolder onCreateViewHolder(Context context, ViewGroup parent) {
        View v = View.inflate(context, R.layout.recents_dummy, null);
        return new CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CardViewHolder cardViewHolder) {
        //cardViewHolder.view.setBackgroundColor(Color.argb(255, 240, 240, 240));
        if (position == 0) {
            cardViewHolder.view.setBackground(context.getResources().getDrawable(R.drawable.qst_card_background_1));
        }else if(position == 1){
          cardViewHolder.view.setBackground(context.getResources().getDrawable(R.drawable.qst_card_background_2));
        } else if (position == 2){
            cardViewHolder.view.setBackground(context.getResources().getDrawable(R.drawable.qst_card_background_5));
        }
        else if (position == 3){
            cardViewHolder.view.setBackground(context.getResources().getDrawable(R.drawable.qst_card_background_3));
        }
        else if (position == 4){
            cardViewHolder.view.setBackground(context.getResources().getDrawable(R.drawable.qst_card_background_4));
        }
        final QstData qstData = cardViewHolder.model;
        switch (qstData.getType().getIndex()){
            case QuestionType.fillin_index:
                cardViewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startStudy(qstData.getPaperId(),qstData.getType().getIndex(),0);
                    }
                });
                break;
        }
//        cardViewHolder.btn.setText("testbtn");
//        cardViewHolder.btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cardViewHolder.btn.setText("test");
//            }
//        });
    }
    private void startStudy(String paperId, int type, int qstNum){
        Log.d(LogUtil.PREFIX+TAG,"----->>"+paperId + " "+ type + " "+ qstNum);
        Intent intent = new Intent(context,AnswerStudyActivity.class);
        if (paperId != null && !paperId.equals("")) {
            intent.putExtra("paperId", paperId);
            intent.putExtra("qstType", type);
            intent.putExtra("qstNum",qstNum);
        }else {
            return;
        }
        context.startActivity(intent);
    }

    public void bindViewHolder(CardViewHolder vh, int position) {
        this.position = position;
        vh.model = qstDatas.get(position);
        onBindViewHolder(vh);
    }

}
