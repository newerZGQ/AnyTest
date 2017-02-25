package com.zgq.wokao.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.model.paper.MultChoQuestion;
import com.zgq.wokao.model.paper.MyQuestionAnswer;
import com.zgq.wokao.model.paper.Question;
import com.zgq.wokao.model.paper.QuestionAnswer;
import com.zgq.wokao.ui.view.QuestionOptionView;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by zgq on 16-7-18.
 */
public class MultChoQuestionAdapter extends PagerAdapter implements BaseStudySystemAdapter, View.OnClickListener {
    private ArrayList<Question> datas = null;
    private LinkedList<ViewGroup> mViewCache = null;
    private Context mContext;
    private LayoutInflater mLayoutInflater = null;
    private ArrayList<Boolean> hasShowAnswer = null;
    private ArrayList<QuestionAnswer> myAnswer = new ArrayList<>();

    private ViewGroup currentView = null;
    private int currentPosition = 0;


    private MultiChoQuestionViewHolder holder;

    public MultChoQuestionAdapter(ArrayList<Question> datas, ArrayList<Boolean> hasShowAnswer, ArrayList<QuestionAnswer> myAnswer, Context context) {
        super();
        this.datas = datas;
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
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
            convertView = (ViewGroup) this.mLayoutInflater.inflate(R.layout.viewadapter_multichoquestion_item, null, false);
            TextView questionBody = (TextView) convertView.findViewById(R.id.multichoqst_body);
            LinearLayout questionLayout = (LinearLayout) convertView.findViewById(R.id.options_layout);
            ArrayList<QuestionOptionView> optionViews = new ArrayList<>();
            TextView myAnswerTv = (TextView) convertView.findViewById(R.id.my_multichoquestion_answer);
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
        multiChoQuestionViewHolder.questionBody.setText("" + (position + 1) + ". " + datas.get(position).getBody());
        //初始化选项View
        LinearLayout layout = multiChoQuestionViewHolder.optionLayout;
        layout.removeAllViewsInLayout();
        ArrayList<QuestionOptionView> optionViews = multiChoQuestionViewHolder.optionViews;
        optionViews.clear();
        for (int i = 0; i < multChoQuestion.getOptionsCount(); i++) {
            QuestionOptionView optionView = new QuestionOptionView(mContext);
            optionView.setContent(getLabelFromPosition(i), multChoQuestion.getOptions().get(i).toString());
            //setTag 标识位置
            optionView.setTag(i);
            optionViews.add(optionView);
            layout.addView(optionView);
        }
        for (QuestionOptionView view : optionViews) {
            view.setOnClickListener(this);
        }
        if (hasShowAnswer.get(position)) {
            String s = myAnswer.get(position).getAnswer();
            multiChoQuestionViewHolder.myAnswerTv.setText(s);
            int[] correctAnswer = getRealAnswerPosition(getRealAnswer(datas.get(position).getAnswer()));
            for (int j = 0; j < correctAnswer.length; j++) {
                optionViews.get(correctAnswer[j]).setToCorrect();
            }
        }else{
            multiChoQuestionViewHolder.myAnswerTv.setText("");
            for (int i =0 ;i<optionViews.size();i++){
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
    public void showCurrentAnswer() {
        int currentPosition = getCurrentPosition();
        int[] correctAnswer = getRealAnswerPosition(getRealAnswer(datas.get(currentPosition).getAnswer()));
        View view = getCurrentView();
        MultiChoQuestionViewHolder holder = (MultiChoQuestionViewHolder) view.getTag();
        ArrayList<QuestionOptionView> optionViews = holder.optionViews;
        for (int j = 0; j < correctAnswer.length; j++) {
            optionViews.get(correctAnswer[j]).setToCorrect();
        }
        holder.myAnswerTv.setText(myAnswer.get(currentPosition).getAnswer());
        hasShowAnswer.set(currentPosition,true);
    }

    @Override
    public void hideCurrentAnswer() {

    }

    @Override
    public void onClick(View v) {
//        for (int i = 0;i<myAnswer.size();i++){
//            Log.d("--------->>>",""+myAnswer.get(i).getAnswer());
//        }
        int currentPosition = getCurrentPosition();
        if (hasShowAnswer.get(currentPosition)) return;
//        Log.d("------position",""+currentPosition);
        MyQuestionAnswer answer = (MyQuestionAnswer) myAnswer.get(currentPosition);

        String answerContent = answer.getAnswer();
//        Log.d("------content",""+answerContent);

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
        answer.setAnswer(getRealAnswer(answerContent));
        myAnswer.set(currentPosition,answer);
//        for (int i = 0;i<myAnswer.size();i++){
//            Log.d("--------->>>",""+myAnswer.get(i).getAnswer());
//        }
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
//            Log.d("------answerposition>",""+result[i]);
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
//        Log.d("--------->>",result);
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

    public final class MultiChoQuestionViewHolder {
        public LinearLayout optionLayout;
        public TextView questionBody;
        public ArrayList<QuestionOptionView> optionViews;
        public TextView myAnswerTv;
    }

}
