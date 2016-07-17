package com.zgq.wokao.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.data.Question;
import com.zgq.wokao.data.QuestionAnswer;
import com.zgq.wokao.data.SglChoQuestion;
import com.zgq.wokao.view.QuestionOptionView;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by zgq on 16-7-6.
 */
public class SglChoQuestionAdapter extends PagerAdapter implements BaseStudySystemAdapter{
    private ArrayList<Question> datas = null;
    private LinkedList<ViewGroup> mViewCache = null;
    private Context mContext ;
    private LayoutInflater mLayoutInflater = null;
    private ArrayList<Boolean> hasShowAnswer = null;
    private ArrayList<QuestionAnswer> myAnswer = new ArrayList<>();

    private ViewGroup currentView = null;
    private int currentPosition = 0;


    private SglChoQuestionViewHolder holder;

    public SglChoQuestionAdapter(ArrayList<Question> datas, ArrayList<Boolean> hasShowAnswer, ArrayList<QuestionAnswer> myAnswer, Context context) {
        super();
        this.datas = datas;
        this.mContext = context ;
        this.mLayoutInflater = LayoutInflater.from(mContext) ;
        this.mViewCache = new LinkedList<>();
        this.hasShowAnswer = hasShowAnswer;
        this.myAnswer = myAnswer;
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
        return getSglChoQuestionView(container,position);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewGroup contentView = (ViewGroup) object;
        container.removeView(contentView);
        this.mViewCache.add(contentView);
    }
    @Override public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    public View getSglChoQuestionView(ViewGroup container, final int position){
        SglChoQuestionViewHolder sglChoQuestionViewHolder = null;
        SglChoQuestion sglChoQuestion = (SglChoQuestion) datas.get(position);
        ViewGroup convertView = null;
        if(mViewCache.size() == 0){
            convertView = (ViewGroup) this.mLayoutInflater.inflate(R.layout.viewadapter_sglchoquestion_item , null ,false);
            TextView questionBody       = (TextView) convertView.findViewById(R.id.sglchoqst_body);
            LinearLayout questionLayout = (LinearLayout) convertView.findViewById(R.id.options_layout);
            sglChoQuestionViewHolder = new SglChoQuestionViewHolder();
            sglChoQuestionViewHolder.optionLayout = questionLayout;
            sglChoQuestionViewHolder.questionBody   = questionBody;
            convertView.setTag(sglChoQuestionViewHolder);
        }else {
            convertView = mViewCache.removeFirst();
            sglChoQuestionViewHolder = (SglChoQuestionViewHolder)convertView.getTag();
        }
        holder = sglChoQuestionViewHolder;
        sglChoQuestionViewHolder.questionBody.setText(""+(position+1)+". "+datas.get(position).getBody());
        if (hasShowAnswer.get(position)) {

        }
        //初始化选项View
        LinearLayout layout = sglChoQuestionViewHolder.optionLayout;
        layout.removeAllViewsInLayout();
        ArrayList<QuestionOptionView> optionViews = new ArrayList<>();
        for (int i = 0;i<sglChoQuestion.getOptionsCount();i++){
            QuestionOptionView optionView = new QuestionOptionView(mContext);
            optionView.setContent(i,sglChoQuestion.getOptions().get(i).toString());
            optionViews.add(optionView);
            layout.addView(optionView);
        }
        container.addView(convertView ,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT );
        return convertView;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        currentView = (ViewGroup) object;
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

    public void showCurrentAnswer(){

    }
    @Override
    public void hideCurrentAnswer() {

    }

    private void clearOptionViews(LinearLayout linearLayout){
        linearLayout.removeAllViewsInLayout();
    }
    public final class SglChoQuestionViewHolder {
        public LinearLayout optionLayout;
        public TextView questionBody;
    }
}
