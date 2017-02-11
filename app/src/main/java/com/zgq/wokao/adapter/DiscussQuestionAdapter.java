package com.zgq.wokao.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.model.Question;

import java.util.ArrayList;
import java.util.LinkedList;

public class DiscussQuestionAdapter extends PagerAdapter implements BaseStudySystemAdapter{
    //显示的数据
    private ArrayList<Question> datas = null;
    private LinkedList<View> mViewCache = null;
    private Context mContext ;
    private LayoutInflater mLayoutInflater = null;
    private ArrayList<Boolean> hasShowAnswer = new ArrayList<>();

    private View currentView = null;
    private int currentPosition = 0;


    private DiscussQuestionViewHolder holder;

    public DiscussQuestionAdapter(ArrayList<Question> datas, ArrayList<Boolean> hasShowAnswer,Context context) {
        super();
        this.datas = datas;
        this.mContext = context ;
        this.mLayoutInflater = LayoutInflater.from(mContext) ;
        this.mViewCache = new LinkedList<>();
        this.hasShowAnswer = hasShowAnswer;
    }

    @Override public int getCount() {
        Log.e("test","getCount ");
        return this.datas.size();
    }
    @Override public int getItemPosition(Object object) {
        Log.e("test","getItemPosition ");
        return super.getItemPosition(object);
    }
    @Override public Object instantiateItem(ViewGroup container, int position) {
        Log.e("test","instantiateItem " + position);
        return getDiscussQuestionView(container,position);
    }
    @Override public void destroyItem(ViewGroup container, int position, Object object) {
        Log.e("test","destroyItem " + position);
        View contentView = (View) object;
        container.removeView(contentView);
        this.mViewCache.add(contentView);
    }
    @Override public boolean isViewFromObject(View view, Object o) {
        Log.e("test","isViewFromObject ");
        return view == o;
    }

    public View getDiscussQuestionView(ViewGroup container, int position){
        DiscussQuestionViewHolder discussQuestionViewHolder = null;
        View convertView = null;
        if(mViewCache.size() == 0){
            convertView = this.mLayoutInflater.inflate(R.layout.viewadapter_discussquestion_item , null ,false);
            TextView questionBody   = (TextView) convertView.findViewById(R.id.discussquestion_body);
            TextView questionAnswer = (TextView) convertView.findViewById(R.id.discussquestion_answer);
            discussQuestionViewHolder = new DiscussQuestionViewHolder();
            discussQuestionViewHolder.questionBody = questionBody;
            discussQuestionViewHolder.questionAnswer = questionAnswer;
            convertView.setTag(discussQuestionViewHolder);
        }else {
            convertView = mViewCache.removeFirst();
            discussQuestionViewHolder = (DiscussQuestionViewHolder)convertView.getTag();
        }
        holder = discussQuestionViewHolder;
        discussQuestionViewHolder.questionBody.setText(datas.get(position).getBody());
        if (hasShowAnswer.get(position)) {
            discussQuestionViewHolder.questionAnswer.setText(datas.get(position).getAnswer());
        }else {
            discussQuestionViewHolder.questionAnswer.setText("");
        }
        container.addView(convertView ,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT );
        return convertView;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        currentView = (View) object;
        currentPosition = position;
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public View getCurrentView(){
        return currentView;
    }
    @Override
    public int getCurrentPosition(){
        return currentPosition;
    }
    @Override
    public void showCurrentAnswer(){
        if (hasShowAnswer.get(currentPosition)) return;
        ((DiscussQuestionViewHolder)(currentView.getTag())).questionAnswer.setText(datas.get(currentPosition).getAnswer());
        hasShowAnswer.set(currentPosition,true);
    }

    @Override
    public void hideCurrentAnswer() {

    }

    public final class DiscussQuestionViewHolder {
        public TextView questionBody;
        public TextView questionAnswer;
    }
}

