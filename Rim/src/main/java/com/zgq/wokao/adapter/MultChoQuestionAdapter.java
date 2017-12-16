package com.zgq.wokao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.entity.paper.question.Answer;
import com.zgq.wokao.entity.paper.question.MultChoQuestion;
import com.zgq.wokao.module.study.entity.StudyInfo;
import com.zgq.wokao.util.DensityUtil;
import com.zgq.wokao.widget.QuestionOptionView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MultChoQuestionAdapter extends BaseViewPagerAdapter<MultChoQuestion> implements View.OnClickListener {
    private ArrayList<MultChoQuestion> datas = null;
    private LinkedList<ViewGroup> mViewCache = null;
    private Context mContext;
    private LayoutInflater mLayoutInflater = null;
    private ArrayList<Boolean> hasShowAnswer = null;
    private ArrayList<Answer> myAnswer = new ArrayList<>();

    private ViewGroup currentView = null;
    private int currentPosition = 0;


    private MultiChoQuestionViewHolder holder;

    public MultChoQuestionAdapter(List<MultChoQuestion> questions) {
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
        return getMultiChoQuestionView(container, position);
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

    public View getMultiChoQuestionView(ViewGroup container, final int position) {
        MultiChoQuestionViewHolder multiChoQuestionViewHolder = null;
        MultChoQuestion multChoQuestion = (MultChoQuestion) datas.get(position);
        ViewGroup convertView = null;
        if (mViewCache.size() == 0) {
            convertView = (ViewGroup) this.mLayoutInflater.inflate(
                    R.layout.viewadapter_multichoquestion_item, null, false);
            TextView questionBody = convertView.findViewById(R.id.multichoqst_body);
            LinearLayout questionLayout = convertView.findViewById(R.id.options_layout);
            ArrayList<QuestionOptionView> optionViews = new ArrayList<>();
            TextView myAnswerTv = convertView.findViewById(R.id.my_multichoquestion_answer);
            multiChoQuestionViewHolder = new MultiChoQuestionViewHolder();
            multiChoQuestionViewHolder.optionLayout = questionLayout;
            multiChoQuestionViewHolder.questionBody = questionBody;
            multiChoQuestionViewHolder.optionViews = optionViews;
            multiChoQuestionViewHolder.myAnswerTv = myAnswerTv;
            convertView.setTag(multiChoQuestionViewHolder);
        } else {
            convertView = mViewCache.removeFirst();
            multiChoQuestionViewHolder = (MultiChoQuestionViewHolder) convertView.getTag();
        }
        holder = multiChoQuestionViewHolder;
        //显示题干
        multiChoQuestionViewHolder.questionBody.setText("" + (position + 1) + ". " + datas.get(position).getBody().getContent());
        //初始化选项View
        LinearLayout layout = multiChoQuestionViewHolder.optionLayout;
        layout.removeAllViewsInLayout();
        ArrayList<QuestionOptionView> optionViews = multiChoQuestionViewHolder.optionViews;
        optionViews.clear();
        for (int i = 0; i < multChoQuestion.getOptions().getOptionList().size(); i++) {
            QuestionOptionView optionView = new QuestionOptionView(mContext);
            optionView.setContent(getLabelFromPosition(i), multChoQuestion.getOptions().getOptionList().get(i).toString());
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
            String s = myAnswer.get(position).getContent();
            multiChoQuestionViewHolder.myAnswerTv.setText(s);
            int[] correctAnswer = getRealAnswerPosition(getRealAnswer(datas.get(position).getAnswer().getContent()));
            for (int j = 0; j < correctAnswer.length; j++) {
                optionViews.get(correctAnswer[j]).setToCorrect();
            }
        } else {
            multiChoQuestionViewHolder.myAnswerTv.setText("");
            for (int i = 0; i < optionViews.size(); i++) {
                optionViews.get(i).setUnselected();
            }
        }
        container.addView(convertView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return convertView;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        currentView = (ViewGroup) object;
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
        boolean isCorrect = false;
        int currentPosition = getCurrentPosition();
        int[] correctAnswer = getRealAnswerPosition(getRealAnswer(datas.get(currentPosition).getAnswer().getContent()));
        View view = getCurrentView();
        MultiChoQuestionViewHolder holder = (MultiChoQuestionViewHolder) view.getTag();
        ArrayList<QuestionOptionView> optionViews = holder.optionViews;
        for (int j = 0; j < correctAnswer.length; j++) {
            optionViews.get(correctAnswer[j]).setToCorrect();
        }
        holder.myAnswerTv.setText(myAnswer.get(currentPosition).getContent());
        hasShowAnswer.set(currentPosition, true);

        int[] thisAnswer = getRealAnswerPosition(getRealAnswer(myAnswer.get(currentPosition).getContent()));
        if (thisAnswer.length == correctAnswer.length) {
            for (int i = 0; i < thisAnswer.length; i++) {
                if (thisAnswer[i] != correctAnswer[i]) {
                    isCorrect = false;
                } else {
                    isCorrect = true;
                }
            }
        }
        if (isCorrect) {
            //getCorrectAnswer(getPaperId(), datas.get(currentPosition));
        } else {
            //getFalseAnswer(getPaperId(), datas.get(currentPosition));
        }
        return isCorrect;
    }

    @Override
    public void hideCurrentAnswer() {

    }

    @Override
    public void onClick(View v) {
        int currentPosition = getCurrentPosition();
        if (hasShowAnswer.get(currentPosition)) return;
        Answer answer = myAnswer.get(currentPosition);

        String answerContent = answer.getContent();

        QuestionOptionView view = (QuestionOptionView) v;
        int optionPostion = (int) view.getTag();
        String optionLabel = getLabelFromPosition(optionPostion);

        int mode = view.getCurrentMode();
        if (mode == QuestionOptionView.SELECTED) {
            view.setUnselected();
            answerContent = answerContent.replace(optionLabel.charAt(0), (char) 32);
        }
        if (mode == QuestionOptionView.UNSELECTED) {
            view.setSelected();
            answerContent = answerContent + optionLabel;
        }
        answer.setContent(getRealAnswer(answerContent));
        myAnswer.set(currentPosition, answer);
    }

    private String getLabelFromPosition(int optionPosition) {
        return String.valueOf((char) (optionPosition + 65));
    }

    private int getOptionPositionFromLabel(String label) {
        char a = label.charAt(0);
        return ((int) a - 65);
    }

    private int[] getRealAnswerPosition(String string) {
        char[] chars = string.toCharArray();
        int[] result = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            result[i] = chars[i] - 65;
        }
        return result;
    }

    private String getRealAnswer(String string) {
        String result = "";
        char[] chars = string.toCharArray();
        sortCharArray(chars);

        for (char c : chars) {
            if (c >= 65 && c <= 90)
                result = result + String.valueOf(c);
        }
        return result;
    }

    private char[] sortCharArray(char[] chars) {
        for (int i = 0; i < chars.length; i++)
            for (int j = i + 1; j < chars.length; j++) {
                if (chars[i] > chars[j]) {
                    char tmp = chars[i];
                    chars[i] = chars[j];
                    chars[j] = tmp;
                }
            }
        return chars;
    }

    @Override
    public int getLastPosition() {
        return datas.get(currentPosition).getInfo().getIndex() - 1;
    }

    public final class MultiChoQuestionViewHolder {
        public LinearLayout optionLayout;
        public TextView questionBody;
        public ArrayList<QuestionOptionView> optionViews;
        public TextView myAnswerTv;
    }

}
