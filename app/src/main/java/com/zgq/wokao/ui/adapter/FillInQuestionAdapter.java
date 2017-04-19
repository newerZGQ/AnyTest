package com.zgq.wokao.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.model.paper.question.IQuestion;

import java.util.ArrayList;
import java.util.LinkedList;

public class FillInQuestionAdapter extends BaseViewPagerAdapter {
    //显示的数据
    private ArrayList<IQuestion> datas = null;
    private LinkedList<View> mViewCache = null;
    private Context mContext ;
    private LayoutInflater mLayoutInflater = null;
    private ArrayList<Boolean> hasShowAnswer = new ArrayList<>();

    private View currentView = null;
    private int currentPosition = 0;


    private FillInQuestionViewHolder holder;

    public FillInQuestionAdapter(ArrayList<IQuestion> datas, ArrayList<Boolean> hasShowAnswer, Context context) {
        super();
        this.datas = datas;
        this.mContext = context ;
        this.mLayoutInflater = LayoutInflater.from(mContext) ;
        this.mViewCache = new LinkedList<>();
        this.hasShowAnswer = hasShowAnswer;
    }

    @Override public int getCount() {
//        Log.e("test","getCount ");
        return this.datas.size();
    }
    @Override public int getItemPosition(Object object) {
//        Log.e("test","getItemPosition ");
        return super.getItemPosition(object);
    }
    @Override public Object instantiateItem(ViewGroup container, int position) {
//        Log.e("test","instantiateItem " + position);
        return getFillInQuestionView(container,position);
    }
    @Override public void destroyItem(ViewGroup container, int position, Object object) {
//        Log.e("test","destroyItem " + position);
        View contentView = (View) object;
        container.removeView(contentView);
        this.mViewCache.add(contentView);
    }
    @Override public boolean isViewFromObject(View view, Object o) {
//        Log.e("test","isViewFromObject ");
        return view == o;
    }

    public View getFillInQuestionView(ViewGroup container, int position){
        FillInQuestionViewHolder fillInQuestionViewHolder = null;
        View convertView = null;
        if(mViewCache.size() == 0){
            convertView = this.mLayoutInflater.inflate(R.layout.viewadapter_fillinquestion_item , null ,false);
            TextView questionBody   = (TextView) convertView.findViewById(R.id.fillinquestion_body);
            TextView questionAnswer = (TextView) convertView.findViewById(R.id.fillinquestion_answer);
            fillInQuestionViewHolder = new FillInQuestionViewHolder();
            fillInQuestionViewHolder.questionBody = questionBody;
            fillInQuestionViewHolder.questionAnswer = questionAnswer;
            convertView.setTag(fillInQuestionViewHolder);
        }else {
            convertView = mViewCache.removeFirst();
            fillInQuestionViewHolder = (FillInQuestionViewHolder)convertView.getTag();
        }
        holder = fillInQuestionViewHolder;
        fillInQuestionViewHolder.questionBody.setText(""+(position+1)+". "+datas.get(position).getBody().getContent());
        if (hasShowAnswer.get(position)) {
            fillInQuestionViewHolder.questionAnswer.setText(datas.get(position).getAnswer().getContent());
        }else {
            fillInQuestionViewHolder.questionAnswer.setText("");
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
    public boolean showCurrentAnswer(){
        if (hasShowAnswer.get(currentPosition)) return false;
        ((FillInQuestionViewHolder)(currentView.getTag())).questionAnswer.setText(datas.get(currentPosition).getAnswer().getContent());
        hasShowAnswer.set(currentPosition,true);
        getCorrectAnswer(getPaperId(),datas.get(currentPosition));
        return true;
    }

    @Override
    public void hideCurrentAnswer() {

    }

    public final class FillInQuestionViewHolder {
        public TextView questionBody;
        public TextView questionAnswer;
    }
}
