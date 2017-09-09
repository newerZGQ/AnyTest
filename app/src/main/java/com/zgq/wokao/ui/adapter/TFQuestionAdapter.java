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
import com.zgq.wokao.model.paper.question.IQuestion;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by zgq on 16-7-6.
 */
public class TFQuestionAdapter extends BaseViewPagerAdapter {
    private ArrayList<IQuestion> datas = null;
    private LinkedList<View> mViewCache = null;
    private Context mContext ;
    private LayoutInflater mLayoutInflater = null;
    private ArrayList<Boolean> hasShowAnswer = null;
    private ArrayList<IAnswer> myAnswer = new ArrayList<>();

    private View currentView = null;
    private int currentPosition = 0;


    private TFQuestionViewHolder holder;

    public TFQuestionAdapter(ArrayList<IQuestion> datas, ArrayList<Boolean> hasShowAnswer, ArrayList<IAnswer> myAnswer, Context context) {
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
        return getTFQuestionView(container,position);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View contentView = (View) object;
        container.removeView(contentView);
        this.mViewCache.add(contentView);
    }
    @Override public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    public View getTFQuestionView(ViewGroup container, final int position){
        TFQuestionViewHolder tfQuestionViewHolder = null;
        View convertView = null;
        if(mViewCache.size() == 0){
            convertView = this.mLayoutInflater.inflate(R.layout.viewadapter_tfquestion_item , null ,false);
            TextView questionBody       = (TextView) convertView.findViewById(R.id.tfquestion_body);
            LinearLayout optionTrue     = (LinearLayout) convertView.findViewById(R.id.tfqst_option_true);
            LinearLayout optionFalse    = (LinearLayout) convertView.findViewById(R.id.tfqst_option_false);
            tfQuestionViewHolder = new TFQuestionViewHolder();
            tfQuestionViewHolder.questionBody   = questionBody;
            tfQuestionViewHolder.optionTrue     = optionTrue;
            tfQuestionViewHolder.optionFalse    = optionFalse;
            convertView.setTag(tfQuestionViewHolder);
        }else {
            convertView = mViewCache.removeFirst();
            tfQuestionViewHolder = (TFQuestionViewHolder)convertView.getTag();
        }
        holder = tfQuestionViewHolder;
        tfQuestionViewHolder.questionBody.setText(""+(position+1)+". "+datas.get(position).getBody().getContent());
        tfQuestionViewHolder.optionFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasShowAnswer.get(position)) return;
                onSelectedFalseOption(v,position);
            }
        });
        tfQuestionViewHolder.optionTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasShowAnswer.get(position)) return;
                onSelectedTrueOption(v,position);
            }
        });
        if (hasShowAnswer.get(position)) {
            String answer = myAnswer.get(position).getContent();
            String answer1 = getRealAnswer(datas.get(position).getAnswer().getContent());
            TextView trueLabel = (TextView) holder.optionTrue.findViewById(R.id.tfqst_option_true_label);
            TextView falseLabel = (TextView) holder.optionFalse.findViewById(R.id.tfqst_option_false_label);
            if (answer.equals(answer1)){
                switch (answer){
                    case "true":
                        trueLabel.setBackground(mContext.getResources().getDrawable(R.drawable.tfquestion_viewpager_selected_right_label));
                        trueLabel.setText("");
                        break;
                    case "false":
                        falseLabel.setBackground(mContext.getResources().getDrawable(R.drawable.tfquestion_viewpager_selected_right_label));
                        falseLabel.setText("");
                }
            }else{
                switch (answer){
                    case "true":
                        trueLabel.setBackground(mContext.getResources().getDrawable(R.drawable.tfquestion_viewpager_selected_wrong_label));
                        trueLabel.setText("");
                        falseLabel.setBackground(mContext.getResources().getDrawable(R.drawable.tfquestion_viewpager_selected_right_label));
                        falseLabel.setText("");
                        break;
                    case "false":
                        falseLabel.setBackground(mContext.getResources().getDrawable(R.drawable.tfquestion_viewpager_selected_wrong_label));
                        falseLabel.setText("");
                        trueLabel.setBackground(mContext.getResources().getDrawable(R.drawable.tfquestion_viewpager_selected_right_label));
                        trueLabel.setText("");
                }
            }
        }else{
            TextView trueLabel = (TextView) holder.optionTrue.findViewById(R.id.tfqst_option_true_label);
            trueLabel.setText("A");
            trueLabel.setBackground(mContext.getResources().getDrawable(R.drawable.activity_answer_study_option_circle_base));
            TextView falseLabel = (TextView) holder.optionFalse.findViewById(R.id.tfqst_option_false_label);
            falseLabel.setText("B");
            falseLabel.setBackground(mContext.getResources().getDrawable(R.drawable.activity_answer_study_option_circle_base));
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

    public void onSelectedTrueOption(View view,int position){
        MyAnswer answer = new MyAnswer();
        answer.setContent("true");
        myAnswer.set(position,answer);
        hasShowAnswer.set(position,true);
        View view1 = getCurrentView();
        TFQuestionViewHolder holder =(TFQuestionViewHolder) view1.getTag();
        final TextView trueLabel = (TextView) holder.optionTrue.findViewById(R.id.tfqst_option_true_label);
        final TextView falseLabel = (TextView) holder.optionFalse.findViewById(R.id.tfqst_option_false_label);

        if (answer.getContent().equals(getRealAnswer(datas.get(position).getAnswer().getContent()))){
            holder.optionFalse.postDelayed(new Runnable() {
                @Override
                public void run() {
                    trueLabel.setBackground(mContext.getResources().getDrawable(R.drawable.tfquestion_viewpager_selected_right_label));
                    trueLabel.setText("");
                }
            },200);
            getCorrectAnswer(getPaperId(),datas.get(currentPosition));
        }else {
            holder.optionFalse.postDelayed(new Runnable() {
                @Override
                public void run() {
                    trueLabel.setBackground(mContext.getResources().getDrawable(R.drawable.tfquestion_viewpager_selected_wrong_label));
                    trueLabel.setText("");
                    falseLabel.setBackground(mContext.getResources().getDrawable(R.drawable.tfquestion_viewpager_selected_right_label));
                    falseLabel.setText("");
                }
            },200);
            getFalseAnswer(getPaperId(),datas.get(currentPosition));
        }
    }
    public void onSelectedFalseOption(View view,int position){
        MyAnswer answer = new MyAnswer();
        answer.setContent("false");
        myAnswer.set(position,answer);
        hasShowAnswer.set(position,true);
        View view1 = getCurrentView();
        TFQuestionViewHolder holder =(TFQuestionViewHolder) view1.getTag();
        final TextView trueLabel = (TextView) holder.optionTrue.findViewById(R.id.tfqst_option_true_label);
        final TextView falseLabel = (TextView) holder.optionFalse.findViewById(R.id.tfqst_option_false_label);

        if (answer.getContent().equals(getRealAnswer(datas.get(position).getAnswer().getContent()))){
            holder.optionFalse.postDelayed(new Runnable() {
                @Override
                public void run() {
                    falseLabel.setBackground(mContext.getResources().
                            getDrawable(R.drawable.tfquestion_viewpager_selected_right_label));
                    falseLabel.setText("");
                }
            },200);
            getCorrectAnswer(getPaperId(),datas.get(currentPosition));
        }else {
            holder.optionFalse.postDelayed(new Runnable() {
                @Override
                public void run() {
                    trueLabel.setBackground(mContext.getResources().
                            getDrawable(R.drawable.tfquestion_viewpager_selected_right_label));
                    trueLabel.setText("");
                    falseLabel.setBackground(mContext.getResources().
                            getDrawable(R.drawable.tfquestion_viewpager_selected_wrong_label));
                    falseLabel.setText("");
                }
            },200);
            getFalseAnswer(getPaperId(),datas.get(currentPosition));
        }
    }

    private String getRealAnswer(String s){
        if (s.equals("F") || s.contains("错") || s.contains("不")  || s.contains("没")){
            return "false";
        }else{
            return "true";
        }
    }

    public boolean showCurrentAnswer(){
        return false;
    }
    @Override
    public void hideCurrentAnswer() {

    }

    @Override
    public int getLastPosition() {
        return datas.get(currentPosition).getInfo().getQstId()-1;
    }

    public final class TFQuestionViewHolder {
        public TextView questionBody;
        public LinearLayout optionTrue;
        public LinearLayout optionFalse;
    }
}
