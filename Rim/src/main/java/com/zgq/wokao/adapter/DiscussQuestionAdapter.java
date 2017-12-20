package com.zgq.wokao.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.entity.paper.question.Answer;
import com.zgq.wokao.entity.paper.question.DiscussQuestion;

import java.util.List;

public class DiscussQuestionAdapter extends BaseViewPagerAdapter<DiscussQuestion> {
    private DiscussQuestionViewHolder holder;

    public DiscussQuestionAdapter(List<DiscussQuestion> questions, OnStudiedListener listener) {
        super(questions,listener);
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
        super.instantiateItem(container,position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.viewadapter_discussquestion_item, null, false);
            TextView questionBody = convertView.findViewById(R.id.discussquestion_body);
            TextView questionAnswer = convertView.findViewById(R.id.discussquestion_answer);
            discussQuestionViewHolder = new DiscussQuestionViewHolder();
            discussQuestionViewHolder.questionBody = questionBody;
            discussQuestionViewHolder.questionAnswer = questionAnswer;
            convertView.setTag(discussQuestionViewHolder);
        } else {
            convertView = mViewCache.removeFirst();
            discussQuestionViewHolder = (DiscussQuestionViewHolder) convertView.getTag();
        }
        holder = discussQuestionViewHolder;
        DiscussQuestion question = studyInfo.getQuestions().get(position);
        discussQuestionViewHolder.questionBody.setText(question.getBody().getContent());
        if (studyInfo.hasAnswered(question.getId())) {
            discussQuestionViewHolder.questionAnswer.setText(question.getAnswer().getContent());
        } else {
            discussQuestionViewHolder.questionAnswer.setText("");
        }
        container.addView(convertView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return convertView;
    }

    @Override
    public void showCurrentAnswer() {
        DiscussQuestion question = studyInfo.getQuestions().get(currentPosition);
        if (studyInfo.hasAnswered(question.getId())) return;
        ((DiscussQuestionViewHolder) (currentView.getTag())).questionAnswer.
                setText(question.getAnswer().getContent());
        studyInfo.saveMyAnswer(question.getId(),new Answer());
        studiedListener.onStudied(question,true);
    }

    @Override
    public void starCurrentQuestion() {
        studiedListener.starQuestion(studyInfo.getQuestions().get(currentPosition));
    }

    public final class DiscussQuestionViewHolder {
        public TextView questionBody;
        public TextView questionAnswer;
    }
}

