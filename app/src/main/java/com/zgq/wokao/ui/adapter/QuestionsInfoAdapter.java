package com.zgq.wokao.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.model.viewdate.QstData;

import java.util.List;

/**
 * Created by zgq on 2017/8/23.
 */

public class QuestionsInfoAdapter extends RecyclerView.Adapter {
    private List<QstData> qstDatas;

    public QuestionsInfoAdapter(List<QstData> qstDatas) {
        this.qstDatas = qstDatas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recyclerview_questions_item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position % 2 == 0) {
            ((MyViewHolder) holder).outLayout.setBackgroundColor(0x6CCF6E);
        }

        ((MyViewHolder) holder).qstType.setText(qstDatas.get(position).getType().getName() + "题");
        ((MyViewHolder) holder).qstInfo.
                setText("共" + qstDatas.get(position).getQstCount() +
                        "题，收藏" + qstDatas.get(position).getStarCount() + "题");

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView qstType;
        public TextView outLayout;
        public TextView qstInfo;

        public MyViewHolder(View itemView) {
            super(itemView);
            qstType = (TextView) itemView.findViewById(R.id.question_type_text);
            outLayout = (TextView) itemView.findViewById(R.id.out_layout);
            qstInfo = (TextView) itemView.findViewById(R.id.qst_info);
        }
    }
}
