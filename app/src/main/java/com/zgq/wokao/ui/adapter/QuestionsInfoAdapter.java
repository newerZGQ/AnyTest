package com.zgq.wokao.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zgq.wokao.R;

import org.w3c.dom.Text;

/**
 * Created by zgq on 2017/8/23.
 */

public class QuestionsInfoAdapter extends RecyclerView.Adapter {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recyclerview_questions_item,null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView qstType;

        public ViewHolder(View itemView) {
            super(itemView);
            qstType = (TextView) itemView.findViewById(R.id.question_type);
        }
    }
}
