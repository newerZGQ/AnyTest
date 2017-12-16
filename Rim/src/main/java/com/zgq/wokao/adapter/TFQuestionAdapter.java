package com.zgq.wokao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.entity.paper.question.Answer;
import com.zgq.wokao.entity.paper.question.TFQuestion;
import com.zgq.wokao.module.study.entity.StudyInfo;

import java.util.LinkedList;

public class TFQuestionAdapter extends BaseViewPagerAdapter<TFQuestion> {
    private LinkedList<View> mViewCache = new LinkedList<>();
    private Context context;

    private View currentView = null;
    private int currentPosition = 0;

    private TFQuestionViewHolder holder;

    public TFQuestionAdapter(StudyInfo studyInfo) {
        super(studyInfo);
    }

    @Override
    public int getCount() {
        return studyInfo.getQuestions().size();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return getTFQuestionView(container, position);
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

    public View getTFQuestionView(ViewGroup container, final int position) {
        TFQuestionViewHolder tfQuestionViewHolder = null;
        View convertView = null;
        context = container.getContext();
        if (mViewCache.size() == 0) {
            convertView = LayoutInflater.from(container.getContext())
                    .inflate(R.layout.viewadapter_tfquestion_item, null, false);
            TextView questionBody = convertView.findViewById(R.id.tfquestion_body);
            LinearLayout optionTrue = convertView.findViewById(R.id.tfqst_option_true);
            LinearLayout optionFalse = convertView.findViewById(R.id.tfqst_option_false);
            tfQuestionViewHolder = new TFQuestionViewHolder();
            tfQuestionViewHolder.questionBody = questionBody;
            tfQuestionViewHolder.optionTrue = optionTrue;
            tfQuestionViewHolder.optionFalse = optionFalse;
            convertView.setTag(tfQuestionViewHolder);
        } else {
            convertView = mViewCache.removeFirst();
            tfQuestionViewHolder = (TFQuestionViewHolder) convertView.getTag();
        }
        holder = tfQuestionViewHolder;
        TFQuestion question = studyInfo.getQuestions().get(position);
        tfQuestionViewHolder.questionBody.setText("" + (position + 1) + ". " + question.getBody().getContent());
        tfQuestionViewHolder.optionFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (studyInfo.hasAnswered(question.getId())) return;
                onSelectedFalseOption(v, position);
            }
        });
        tfQuestionViewHolder.optionTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (studyInfo.hasAnswered(question.getId())) return;
                onSelectedTrueOption(v, position);
            }
        });
        if (studyInfo.hasAnswered(question.getId())) {
            String answer = studyInfo.getMyAnswer(question.getId()).getContent();
            String answer1 = getRealAnswer(question.getAnswer().getContent());
            TextView trueLabel = holder.optionTrue.findViewById(R.id.tfqst_option_true_label);
            TextView falseLabel = holder.optionFalse.findViewById(R.id.tfqst_option_false_label);
            if (answer.equals(answer1)) {
                switch (answer) {
                    case "true":
                        trueLabel.setBackground(context.getResources().getDrawable(R.drawable.tfquestion_viewpager_selected_right_label));
                        trueLabel.setText("");
                        break;
                    case "false":
                        falseLabel.setBackground(context.getResources().getDrawable(R.drawable.tfquestion_viewpager_selected_right_label));
                        falseLabel.setText("");
                }
            } else {
                switch (answer) {
                    case "true":
                        trueLabel.setBackground(context.getResources().getDrawable(R.drawable.tfquestion_viewpager_selected_wrong_label));
                        trueLabel.setText("");
                        falseLabel.setBackground(context.getResources().getDrawable(R.drawable.tfquestion_viewpager_selected_right_label));
                        falseLabel.setText("");
                        break;
                    case "false":
                        falseLabel.setBackground(context.getResources().getDrawable(R.drawable.tfquestion_viewpager_selected_wrong_label));
                        falseLabel.setText("");
                        trueLabel.setBackground(context.getResources().getDrawable(R.drawable.tfquestion_viewpager_selected_right_label));
                        trueLabel.setText("");
                }
            }
        } else {
            TextView trueLabel = holder.optionTrue.findViewById(R.id.tfqst_option_true_label);
            trueLabel.setText("A");
            trueLabel.setBackground(context.getResources().getDrawable(R.drawable.activity_answer_study_option_circle_base));
            TextView falseLabel = holder.optionFalse.findViewById(R.id.tfqst_option_false_label);
            falseLabel.setText("B");
            falseLabel.setBackground(context.getResources().getDrawable(R.drawable.activity_answer_study_option_circle_base));
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

    public void onSelectedTrueOption(View view, int position) {
        Answer answer = new Answer();
        answer.setContent("true");
        TFQuestion question = studyInfo.getQuestions().get(position);
        studyInfo.saveMyAnswer(question.getId(),answer);
        View view1 = getCurrentView();
        TFQuestionViewHolder holder = (TFQuestionViewHolder) view1.getTag();
        final TextView trueLabel = (TextView) holder.optionTrue.findViewById(R.id.tfqst_option_true_label);
        final TextView falseLabel = (TextView) holder.optionFalse.findViewById(R.id.tfqst_option_false_label);

        if (answer.getContent().equals(getRealAnswer(question.getAnswer().getContent()))) {
            holder.optionFalse.postDelayed(new Runnable() {
                @Override
                public void run() {
                    trueLabel.setBackground(context.getResources().getDrawable(R.drawable.tfquestion_viewpager_selected_right_label));
                    trueLabel.setText("");
                }
            }, 200);
            //getCorrectAnswer(getPaperId(), datas.get(currentPosition));
        } else {
            holder.optionFalse.postDelayed(new Runnable() {
                @Override
                public void run() {
                    trueLabel.setBackground(context.getResources().getDrawable(R.drawable.tfquestion_viewpager_selected_wrong_label));
                    trueLabel.setText("");
                    falseLabel.setBackground(context.getResources().getDrawable(R.drawable.tfquestion_viewpager_selected_right_label));
                    falseLabel.setText("");
                }
            }, 200);
            //getFalseAnswer(getPaperId(), datas.get(currentPosition));
        }
    }

    public void onSelectedFalseOption(View view, int position) {
        Answer answer = new Answer();
        answer.setContent("false");
        TFQuestion question = studyInfo.getQuestions().get(position);
        studyInfo.saveMyAnswer(question.getId(),answer);
        View view1 = getCurrentView();
        TFQuestionViewHolder holder = (TFQuestionViewHolder) view1.getTag();
        final TextView trueLabel = (TextView) holder.optionTrue.findViewById(R.id.tfqst_option_true_label);
        final TextView falseLabel = (TextView) holder.optionFalse.findViewById(R.id.tfqst_option_false_label);

        if (answer.getContent().equals(getRealAnswer(question.getAnswer().getContent()))) {
            holder.optionFalse.postDelayed(new Runnable() {
                @Override
                public void run() {
                    falseLabel.setBackground(context.getResources().
                            getDrawable(R.drawable.tfquestion_viewpager_selected_right_label));
                    falseLabel.setText("");
                }
            }, 200);
            //getCorrectAnswer(getPaperId(), datas.get(currentPosition));
        } else {
            holder.optionFalse.postDelayed(new Runnable() {
                @Override
                public void run() {
                    trueLabel.setBackground(context.getResources().
                            getDrawable(R.drawable.tfquestion_viewpager_selected_right_label));
                    trueLabel.setText("");
                    falseLabel.setBackground(context.getResources().
                            getDrawable(R.drawable.tfquestion_viewpager_selected_wrong_label));
                    falseLabel.setText("");
                }
            }, 200);
            //getFalseAnswer(getPaperId(), datas.get(currentPosition));
        }
    }

    private String getRealAnswer(String s) {
        if (s.equals("F") || s.contains("错") || s.contains("不") || s.contains("没")) {
            return "false";
        } else {
            return "true";
        }
    }

    public boolean showCurrentAnswer() {
        return false;
    }

    @Override
    public void hideCurrentAnswer() {

    }

    @Override
    public int getLastPosition() {
        return studyInfo.getQuestions().get(currentPosition).getInfo().getIndex() - 1;
    }

    public final class TFQuestionViewHolder {
        public TextView questionBody;
        public LinearLayout optionTrue;
        public LinearLayout optionFalse;
    }
}
