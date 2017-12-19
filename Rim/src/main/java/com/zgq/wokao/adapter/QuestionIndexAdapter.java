package com.zgq.wokao.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.entity.paper.question.IQuestion;

import java.util.HashMap;
import java.util.List;

public class QuestionIndexAdapter extends BaseAdapter{
    private HashMap<IQuestion, Boolean> answered;
    private List<IQuestion> questions;

    public QuestionIndexAdapter(List<IQuestion> questions, HashMap<IQuestion, Boolean> answered) {
        this.answered = answered;
        this.questions = questions;
    }

    public void replaceData(List<IQuestion> questions, HashMap<IQuestion, Boolean> answered){
        this.answered = answered;
        this.questions = questions;
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

        ((TextView) convertView
                .findViewById(R.id.question_position))
                .setText(String.valueOf(questions.get(position).getInfo().getIndex()));
        if (answered.get(questions.get(position))) {
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
        return questions.size();
    }
}
