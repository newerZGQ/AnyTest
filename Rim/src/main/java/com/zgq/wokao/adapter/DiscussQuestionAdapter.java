package com.zgq.wokao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.entity.paper.question.DiscussQuestion;
import com.zgq.wokao.module.study.entity.StudyInfo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DiscussQuestionAdapter extends BaseViewPagerAdapter {
    //显示的数据
    private ArrayList<DiscussQuestion> datas = null;
    private LinkedList<View> mViewCache = null;
    private Context mContext;
    private LayoutInflater mLayoutInflater = null;
    private ArrayList<Boolean> hasShowAnswer = new ArrayList<>();

    private View currentView = null;
    private int currentPosition = 0;


    private DiscussQuestionViewHolder holder;

    public DiscussQuestionAdapter(List<DiscussQuestion> questions) {
        super(questions);
    }

    @Override
    public int getCount() {
        return this.datas.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return getDiscussQuestionView(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View contentView = (View) object;
        container.removeView(contentView);
        this.mViewCache.add(contentView);
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    public View getDiscussQuestionView(ViewGroup container, int position) {
        DiscussQuestionViewHolder discussQuestionViewHolder = null;
        View convertView = null;
        if (mViewCache.size() == 0) {
            convertView = this.mLayoutInflater.inflate(R.layout.viewadapter_discussquestion_item, null, false);
            TextView questionBody = (TextView) convertView.findViewById(R.id.discussquestion_body);
            TextView questionAnswer = (TextView) convertView.findViewById(R.id.discussquestion_answer);
            discussQuestionViewHolder = new DiscussQuestionViewHolder();
            discussQuestionViewHolder.questionBody = questionBody;
            discussQuestionViewHolder.questionAnswer = questionAnswer;
            convertView.setTag(discussQuestionViewHolder);
        } else {
            convertView = mViewCache.removeFirst();
            discussQuestionViewHolder = (DiscussQuestionViewHolder) convertView.getTag();
        }
        holder = discussQuestionViewHolder;
        discussQuestionViewHolder.questionBody.setText(datas.get(position).getBody().getContent());
        if (hasShowAnswer.get(position)) {
            discussQuestionViewHolder.questionAnswer.setText(datas.get(position).getAnswer().getContent());
        } else {
            discussQuestionViewHolder.questionAnswer.setText("");
        }
        container.addView(convertView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return convertView;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        currentView = (View) object;
        currentPosition = position;
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public View getCurrentView() {
        return currentView;
    }

    @Override
    public int getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public boolean showCurrentAnswer() {
        if (hasShowAnswer.get(currentPosition)) return false;
        ((DiscussQuestionViewHolder) (currentView.getTag())).questionAnswer.
                setText(datas.get(currentPosition).getAnswer().getContent());
        hasShowAnswer.set(currentPosition, true);
        //getCorrectAnswer(getPaperId(), datas.get(currentPosition));
        return true;
    }

    @Override
    public void hideCurrentAnswer() {

    }

    @Override
    public int getLastPosition() {
        return datas.get(currentPosition).getInfo().getIndex() - 1;
    }

    public final class DiscussQuestionViewHolder {
        public TextView questionBody;
        public TextView questionAnswer;
    }
}

