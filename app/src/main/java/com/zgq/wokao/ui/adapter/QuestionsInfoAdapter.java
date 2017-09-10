package com.zgq.wokao.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zgq.wokao.MyApplication;
import com.zgq.wokao.R;
import com.zgq.wokao.model.viewdate.QstData;

import java.util.List;

/**
 * Created by zgq on 2017/8/23.
 */

public class QuestionsInfoAdapter extends RecyclerView.Adapter {
    private List<QstData> qstDatas;
    private ItemClickListener clickListener;
    public QuestionsInfoAdapter(List<QstData> qstDatas , ItemClickListener clickListener) {
        this.qstDatas = qstDatas;
        this.clickListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recyclerview_questions_item, null);
        return new MyViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position % 2 == 0) {
            ((MyViewHolder) holder).outLayout.setBackground(MyApplication.getInstance().
                    getResources().getDrawable(R.drawable.question_type_back_1));
        }else{
            ((MyViewHolder) holder).outLayout.setBackground(MyApplication.getInstance().
                    getResources().getDrawable(R.drawable.question_type_back_3));
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

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView qstType;
        public TextView outLayout;
        public TextView qstInfo;
        private ItemClickListener clickListener = new ItemClickListener() {
            @Override
            public void onItemClicked(int position) {

            }
        };

        public MyViewHolder(View itemView, ItemClickListener clickListener) {
            super(itemView);
            this.clickListener = clickListener;
            itemView.setOnClickListener(this);
            qstType = (TextView) itemView.findViewById(R.id.question_type_text);
            outLayout = (TextView) itemView.findViewById(R.id.out_layout);
            qstInfo = (TextView) itemView.findViewById(R.id.qst_info);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClicked(getPosition());
        }
    }

    public interface ItemClickListener{
        void onItemClicked(int position);
    }
}
