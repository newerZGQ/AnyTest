package com.zgq.wokao.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zgq.wokao.R;

import java.util.ArrayList;

public class QuestionIndexAdapter extends BaseAdapter{
    private ArrayList<Boolean> answeredList = new ArrayList();

    public QuestionIndexAdapter(ArrayList<Boolean> answeredList) {
        this.answeredList = answeredList;
    }

    public void replaceData(ArrayList<Boolean> answeredList){
        this.answeredList = answeredList;
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_index_item, null);
        }
        ((TextView) convertView.findViewById(R.id.question_position)).setText("" + (position + 1));
        if (answeredList.get(position)) {
            convertView.findViewById(R.id.question_position).
                    setBackground(parent.getContext().getResources().getDrawable(R.drawable.circle_background_upside_lime));
        }
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        return answeredList.size();
    }
}
