package com.zgq.wokao.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.model.paper.question.answer.IAnswer;
import com.zgq.wokao.model.paper.question.answer.MyAnswer;
import com.zgq.wokao.model.paper.question.impl.SglChoQuestion;
import com.zgq.wokao.model.paper.question.IQuestion;
import com.zgq.wokao.ui.widget.QuestionOptionView;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by zgq on 16-7-6.
 */
public class SglChoQuestionAdapter extends PagerAdapter implements BaseStudySystemAdapter,View.OnClickListener{
    private ArrayList<IQuestion> datas = null;
    private LinkedList<ViewGroup> mViewCache = null;
    private Context mContext ;
    private LayoutInflater mLayoutInflater = null;
    private ArrayList<Boolean> hasShowAnswer = null;
    private ArrayList<IAnswer> myAnswer = new ArrayList<>();

    private ViewGroup currentView = null;
    private int currentPosition = 0;


    private SglChoQuestionViewHolder holder;

    public SglChoQuestionAdapter(ArrayList<IQuestion> datas, ArrayList<Boolean> hasShowAnswer, ArrayList<IAnswer> myAnswer, Context context) {
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
            ArrayList<QuestionOptionView> optionViews = new ArrayList<>();
            sglChoQuestionViewHolder = new SglChoQuestionViewHolder();
            sglChoQuestionViewHolder.optionLayout = questionLayout;
            sglChoQuestionViewHolder.questionBody   = questionBody;
            sglChoQuestionViewHolder.optionViews = optionViews;
            convertView.setTag(sglChoQuestionViewHolder);
        }else {
            convertView = mViewCache.removeFirst();
            sglChoQuestionViewHolder = (SglChoQuestionViewHolder)convertView.getTag();
        }
        holder = sglChoQuestionViewHolder;
        //显示题干
        sglChoQuestionViewHolder.questionBody.setText(""+(position+1)+". "+datas.get(position).getBody());
        //初始化选项View
        LinearLayout layout = sglChoQuestionViewHolder.optionLayout;
        layout.removeAllViewsInLayout();
        ArrayList<QuestionOptionView> optionViews = sglChoQuestionViewHolder.optionViews;
        optionViews.clear();
        for (int i = 0;i<sglChoQuestion.getOptions().getOptionsCount();i++){
            QuestionOptionView optionView = new QuestionOptionView(mContext);
            optionView.setContent(getLabelFromPosition(i),sglChoQuestion.getOptions().getOptionList().get(i).toString());
            //setTag 标识位置
            optionView.setTag(i);
            optionViews.add(optionView);
            layout.addView(optionView);
        }
        for (QuestionOptionView view:optionViews){
            view.setOnClickListener(this);
        }
        if (hasShowAnswer.get(position)) {
            int selected = getOptionPositionFromLabel(myAnswer.get(position).getContent());
            int right    = getOptionPositionFromLabel(sglChoQuestion.getAnswer().getContent());
            if (selected == right){
                for (int i = 0; i<optionViews.size();i++){
                    if (i == selected) optionViews.get(i).setToCorrect();
                }
            }else{
                for (int i = 0; i<optionViews.size();i++){
                    if (i == selected) optionViews.get(i).setToWrong();
                    if (i == right) optionViews.get(i).setToCorrect();
                }
            }
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

    @Override
    public void showCurrentAnswer(){

    }

    /*
    @params selectedOption  用户选择的选项位置，A为0
    @params rightOption     正确答案位置
     */
    public void showCurrentAnswer(int selectedOption,int rightOption){
        ArrayList<QuestionOptionView> optionViews = ((SglChoQuestionViewHolder)getCurrentView().getTag()).optionViews;
        if (selectedOption == rightOption){
            for (int i = 0; i<optionViews.size();i++){
                if (i == selectedOption) optionViews.get(i).setToCorrect();
            }
        }else{
            for (int i = 0; i<optionViews.size();i++){
                if (i == selectedOption) optionViews.get(i).setToWrong();
                if (i == rightOption) optionViews.get(i).setToCorrect();
            }
        }
    }

    @Override
    public void hideCurrentAnswer() {

    }

    @Override
    public void onClick(View v) {
        int currentPosition = getCurrentPosition();
        SglChoQuestion question = (SglChoQuestion) datas.get(currentPosition);
        if (hasShowAnswer.get(currentPosition)) return;

        int selectedOption = (int)v.getTag();
        MyAnswer answer = new MyAnswer();
        answer.setContent(getLabelFromPosition(selectedOption));
        myAnswer.set(getCurrentPosition(),answer);

        showCurrentAnswer(selectedOption,getOptionPositionFromLabel(question.getAnswer().getContent()));

        hasShowAnswer.set(currentPosition,true);
    }

    private String getLabelFromPosition(int optionPosition){
        return String.valueOf((char)(optionPosition+65));
    }

    private int getOptionPositionFromLabel(String label){
        char a = label.charAt(0);
        return ((int)a - 65);
    }
    public final class SglChoQuestionViewHolder {
        public LinearLayout optionLayout;
        public TextView questionBody;
        public ArrayList<QuestionOptionView> optionViews;
    }
}
