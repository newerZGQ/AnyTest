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

/**
 * Created by zgq on 2017/3/31.
 */

public class CardViewAdapter extends OverviewAdapter<CardViewHolder, QstData> {
    private static final String TAG = CardViewAdapter.class.getSimpleName();

    private int position = 0;

    private Context context;
    private List<QstData> qstDatas;

    private OnCardViewClickListener listener;
    public CardViewAdapter(List<QstData> qstDatas,Context context, OnCardViewClickListener listener) {
        super(qstDatas);
        this.context = context;
        this.qstDatas = qstDatas;
        this.listener = listener;
    }

    @Override
    public CardViewHolder onCreateViewHolder(Context context, ViewGroup parent) {
        View v = View.inflate(context, R.layout.recents_dummy, null);
        return new CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CardViewHolder cardViewHolder) {
        final int position = cardViewHolder.getPosition();
        if (position == 4) {
            cardViewHolder.view.setBackground(context.getResources().getDrawable(R.drawable.qst_card_background_fillin));
            cardViewHolder.title.setText("填空题");
            cardViewHolder.title.setTextColor(context.getResources().getColor(R.color.color_cardview_title_fillin));
            cardViewHolder.accuracy.setTextColor(context.getResources().getColor(R.color.color_cardview_title_fillin));
            cardViewHolder.fallables.setTextColor(context.getResources().getColor(R.color.color_cardview_title_fillin));
            cardViewHolder.accuracy.setText("答题次数");

            cardViewHolder.fallables.setVisibility(View.GONE);
            cardViewHolder.fallable_1.setVisibility(View.GONE);
            cardViewHolder.fallable_2.setVisibility(View.GONE);
            cardViewHolder.fallable_3.setVisibility(View.GONE);

            cardViewHolder.accuracyTv.setText(""+qstDatas.get(position).getStudyNum());


        }else if(position == 3){
          cardViewHolder.view.setBackground(context.getResources().getDrawable(R.drawable.qst_card_background_tf));
            cardViewHolder.title.setText("判断题");
            cardViewHolder.title.setTextColor(context.getResources().getColor(R.color.color_cardview_title_tf));
            cardViewHolder.accuracy.setTextColor(context.getResources().getColor(R.color.color_cardview_title_tf));
            cardViewHolder.fallables.setTextColor(context.getResources().getColor(R.color.color_cardview_title_tf));
            cardViewHolder.accuracy.setText("正确率");

            cardViewHolder.fallables.setVisibility(View.VISIBLE);
            setFallablesVisible(cardViewHolder,position);

            cardViewHolder.accuracyTv.setText("" + qstDatas.get(position).getCorrectNum() + "/" + qstDatas.get(position).getStudyNum());
        } else if (position == 2){
            cardViewHolder.view.setBackground(context.getResources().getDrawable(R.drawable.qst_card_background_sgl));
            cardViewHolder.title.setText("单选题");
            cardViewHolder.title.setTextColor(context.getResources().getColor(R.color.color_cardview_title_sgl));
            cardViewHolder.accuracy.setTextColor(context.getResources().getColor(R.color.color_cardview_title_sgl));
            cardViewHolder.fallables.setTextColor(context.getResources().getColor(R.color.color_cardview_title_sgl));
            cardViewHolder.accuracy.setText("正确率");

            cardViewHolder.fallables.setVisibility(View.VISIBLE);
            setFallablesVisible(cardViewHolder,position);

            cardViewHolder.accuracyTv.setText("" + qstDatas.get(position).getCorrectNum() + "/" + qstDatas.get(position).getStudyNum());
        }
        else if (position == 1){
            cardViewHolder.view.setBackground(context.getResources().getDrawable(R.drawable.qst_card_background_mlc));
            cardViewHolder.title.setText("多选题");
            cardViewHolder.title.setTextColor(context.getResources().getColor(R.color.color_cardview_title_mlc));
            cardViewHolder.accuracy.setTextColor(context.getResources().getColor(R.color.color_cardview_title_mlc));
            cardViewHolder.fallables.setTextColor(context.getResources().getColor(R.color.color_cardview_title_mlc));
            cardViewHolder.accuracy.setText("正确率");

            cardViewHolder.fallables.setVisibility(View.VISIBLE);
            setFallablesVisible(cardViewHolder,position);

            cardViewHolder.accuracyTv.setText("" + qstDatas.get(position).getCorrectNum() + "/" + qstDatas.get(position).getStudyNum());
        }
        else if (position == 0){
            cardViewHolder.view.setBackground(context.getResources().getDrawable(R.drawable.qst_card_background_dis));
            cardViewHolder.title.setText("简答题");
            cardViewHolder.title.setTextColor(context.getResources().getColor(R.color.color_cardview_title_dis));
            cardViewHolder.accuracy.setTextColor(context.getResources().getColor(R.color.color_cardview_title_dis));
            cardViewHolder.fallables.setTextColor(context.getResources().getColor(R.color.color_cardview_title_dis));
            cardViewHolder.accuracy.setText("答题次数");

            cardViewHolder.fallables.setVisibility(View.GONE);
            cardViewHolder.fallable_1.setVisibility(View.GONE);
            cardViewHolder.fallable_2.setVisibility(View.GONE);
            cardViewHolder.fallable_3.setVisibility(View.GONE);

            cardViewHolder.accuracyTv.setText("" + qstDatas.get(position).getStudyNum());
        }


        cardViewHolder.fallable_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onSelectedSpeQuestion(qstDatas.get(position).getPaperId(),
                            qstDatas.get(position).getType(),
                            Integer.valueOf(cardViewHolder.fallable_1.getText().toString())-1);
                }
//                startStudy(qstDatas.get(position).getPaperId(),
//                        qstDatas.get(position).getType().getIndex(),
//                        Integer.valueOf(cardViewHolder.fallable_1.getText().toString())-1);
            }
        });
        cardViewHolder.fallable_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onSelectedSpeQuestion(qstDatas.get(position).getPaperId(),
                            qstDatas.get(position).getType(),
                            Integer.valueOf(cardViewHolder.fallable_2.getText().toString())-1);
                }
//                startStudy(qstDatas.get(position).getPaperId(),
//                        qstDatas.get(position).getType().getIndex(),
//                        Integer.valueOf(cardViewHolder.fallable_2.getText().toString())-1);
            }
        });
        cardViewHolder.fallable_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onSelectedSpeQuestion(qstDatas.get(position).getPaperId(),
                            qstDatas.get(position).getType(),
                            Integer.valueOf(cardViewHolder.fallable_3.getText().toString())-1);
                }
//                startStudy(qstDatas.get(position).getPaperId(),
//                        qstDatas.get(position).getType().getIndex(),
//                        Integer.valueOf(cardViewHolder.fallable_3.getText().toString())-1);
            }
        });

    }

    private void setFallablesVisible(CardViewHolder cardViewHolder, int position){
        switch (qstDatas.get(position).getFallibleQsts().size()){
            case 0:
                break;
            case 1:
                cardViewHolder.fallable_1.setVisibility(View.VISIBLE);
                cardViewHolder.fallable_2.setVisibility(View.GONE);
                cardViewHolder.fallable_3.setVisibility(View.GONE);
                cardViewHolder.fallable_1.setText(""+qstDatas.get(position).getFallibleQsts().get(0).getInfo().getQstId());
                break;
            case 2:
                cardViewHolder.fallable_1.setVisibility(View.VISIBLE);
                cardViewHolder.fallable_2.setVisibility(View.VISIBLE);
                cardViewHolder.fallable_3.setVisibility(View.GONE);
                cardViewHolder.fallable_1.setText(""+qstDatas.get(position).getFallibleQsts().get(0).getInfo().getQstId());
                cardViewHolder.fallable_2.setText(""+qstDatas.get(position).getFallibleQsts().get(1).getInfo().getQstId());
                break;
            case 3:
                cardViewHolder.fallable_1.setVisibility(View.VISIBLE);
                cardViewHolder.fallable_2.setVisibility(View.VISIBLE);
                cardViewHolder.fallable_3.setVisibility(View.VISIBLE);
                cardViewHolder.fallable_1.setText(""+qstDatas.get(position).getFallibleQsts().get(0).getInfo().getQstId());
                cardViewHolder.fallable_2.setText(""+qstDatas.get(position).getFallibleQsts().get(1).getInfo().getQstId());
                cardViewHolder.fallable_3.setText(""+qstDatas.get(position).getFallibleQsts().get(2).getInfo().getQstId());
                break;
            default:
                break;
        }
    }

    private void startStudy(String paperId, int type, int qstNum){
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

    public void bindViewHolder(CardViewHolder vh, final int position) {
        this.position = position;

        vh.qstInfo.setText("共"+qstDatas.get(position).getQstCount()+ "题，收藏"+qstDatas.get(position).getStarCount()+"题");

        vh.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LogUtil.isDebug) {
                    Log.d(LogUtil.PREFIX, " type index " + qstDatas.get(position).getType().getIndex());
                    Log.d(LogUtil.PREFIX, " position" + position);
                    Log.d(LogUtil.PREFIX, " view" + view);
                }
                if (listener != null){
                    listener.onSelectedQuestionType(qstDatas.get(position).getPaperId(), qstDatas.get(position).getType());
                }
//                startStudy(qstDatas.get(position).getPaperId(), qstDatas.get(position).getType().getIndex(),0);
            }
        });
        onBindViewHolder(vh);
    }

    public OnCardViewClickListener getListener() {
        return listener;
    }

    public void setListener(OnCardViewClickListener listener) {
        this.listener = listener;
    }

    public interface OnCardViewClickListener{
        public void onSelectedQuestionType(String paperId, QuestionType type);
        public void onSelectedSpeQuestion(String paperId, QuestionType type, int questionIndex);
    }

}
