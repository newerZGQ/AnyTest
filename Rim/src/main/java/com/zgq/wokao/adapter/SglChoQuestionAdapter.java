package com.zgq.wokao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.entity.paper.question.Answer;
import com.zgq.wokao.entity.paper.question.SglChoQuestion;
import com.zgq.wokao.module.study.entity.StudyInfo;
import com.zgq.wokao.util.DensityUtil;
import com.zgq.wokao.widget.QuestionOptionView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SglChoQuestionAdapter extends BaseViewPagerAdapter<SglChoQuestion> implements View.OnClickListener {
    private ArrayList<SglChoQuestion> datas = null;
    private Context mContext;
    private LayoutInflater mLayoutInflater = null;
    private ArrayList<Boolean> hasShowAnswer = null;
    private ArrayList<Answer> myAnswer = new ArrayList<>();


    private SglChoQuestionViewHolder holder;

    public SglChoQuestionAdapter(List<SglChoQuestion> questions, OnStudiedListener listener) {
        super(questions,listener);
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
        return getSglChoQuestionView(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewGroup contentView = (ViewGroup) object;
        container.removeView(contentView);
        this.mViewCache.add(contentView);
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    public View getSglChoQuestionView(ViewGroup container, final int position) {
        SglChoQuestionViewHolder sglChoQuestionViewHolder = null;
        SglChoQuestion sglChoQuestion = datas.get(position);
        View convertView = null;
        if (mViewCache.size() == 0) {
            convertView = (ViewGroup) this.mLayoutInflater.inflate(R.layout.viewadapter_sglchoquestion_item, null, false);
            TextView questionBody = convertView.findViewById(R.id.sglchoqst_body);
            LinearLayout questionLayout = convertView.findViewById(R.id.options_layout);
            ArrayList<QuestionOptionView> optionViews = new ArrayList<>();
            sglChoQuestionViewHolder = new SglChoQuestionViewHolder();
            sglChoQuestionViewHolder.optionLayout = questionLayout;
            sglChoQuestionViewHolder.questionBody = questionBody;
            sglChoQuestionViewHolder.optionViews = optionViews;
            convertView.setTag(sglChoQuestionViewHolder);
        } else {
            convertView = mViewCache.removeFirst();
            sglChoQuestionViewHolder = (SglChoQuestionViewHolder) convertView.getTag();
        }
        holder = sglChoQuestionViewHolder;
        //显示题干
        sglChoQuestionViewHolder.questionBody.setText("" + (position + 1) + ". " + datas.get(position).getBody().getContent());
        //初始化选项View
        LinearLayout layout = sglChoQuestionViewHolder.optionLayout;
        layout.removeAllViewsInLayout();
        ArrayList<QuestionOptionView> optionViews = sglChoQuestionViewHolder.optionViews;
        optionViews.clear();
        for (int i = 0; i < sglChoQuestion.getOptions().getOptionList().size(); i++) {
            QuestionOptionView optionView = new QuestionOptionView(mContext);
            optionView.setContent(getLabelFromPosition(i), sglChoQuestion.getOptions().getOptionList().get(i).toString());
            //setTag 标识位置
            optionView.setTag(i);
            optionViews.add(optionView);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, DensityUtil.dip2px(mContext, 16f), 0, 0);
            layout.addView(optionView, params);
        }
        for (QuestionOptionView view : optionViews) {
            view.setOnClickListener(this);
        }
        if (hasShowAnswer.get(position)) {
            int selected = getOptionPositionFromLabel(myAnswer.get(position).getContent());
            int right = getOptionPositionFromLabel(sglChoQuestion.getAnswer().getContent());
            if (selected == right) {
                for (int i = 0; i < optionViews.size(); i++) {
                    if (i == selected) optionViews.get(i).setToCorrect();
                }
            } else {
                for (int i = 0; i < optionViews.size(); i++) {
                    if (i == selected) optionViews.get(i).setToWrong();
                    if (i == right) optionViews.get(i).setToCorrect();
                }
            }
        }
        container.addView(convertView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return convertView;
    }

    @Override
    public void showCurrentAnswer() {

    }

    @Override
    public void starCurrentQuestion() {
        studiedListener.starQuestion(studyInfo.getQuestions().get(currentPosition));
    }

    /*
    @params selectedOption  用户选择的选项位置，A为0
    @params rightOption     正确答案位置
     */
    private void showCurrentAnswer(int selectedOption, int rightOption) {
        ArrayList<QuestionOptionView> optionViews = ((SglChoQuestionViewHolder) currentView.getTag()).optionViews;
        if (selectedOption == rightOption) {
            for (int i = 0; i < optionViews.size(); i++) {
                if (i == selectedOption) optionViews.get(i).setToCorrect();
            }
            studiedListener.onStudied(studyInfo.getQuestions().get(currentPosition),true);
        } else {
            for (int i = 0; i < optionViews.size(); i++) {
                if (i == selectedOption) optionViews.get(i).setToWrong();
                if (i == rightOption) optionViews.get(i).setToCorrect();
            }
            studiedListener.onStudied(studyInfo.getQuestions().get(currentPosition),false);
        }
    }

    @Override
    public void onClick(View v) {
        SglChoQuestion question = datas.get(currentPosition);
        if (hasShowAnswer.get(currentPosition)) return;

        int selectedOption = (int) v.getTag();
        Answer answer = new Answer();
        answer.setContent(getLabelFromPosition(selectedOption));
        myAnswer.set(currentPosition, answer);

        showCurrentAnswer(selectedOption, getOptionPositionFromLabel(question.getAnswer().getContent()));

        hasShowAnswer.set(currentPosition, true);
    }

    private String getLabelFromPosition(int optionPosition) {
        return String.valueOf((char) (optionPosition + 65));
    }

    private int getOptionPositionFromLabel(String label) {
        char a = label.charAt(0);
        return ((int) a - 65);
    }

    public final class SglChoQuestionViewHolder {
        public LinearLayout optionLayout;
        public TextView questionBody;
        public ArrayList<QuestionOptionView> optionViews;
    }
}
