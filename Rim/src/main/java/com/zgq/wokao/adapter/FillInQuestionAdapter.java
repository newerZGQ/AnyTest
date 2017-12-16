package com.zgq.wokao.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.entity.paper.question.Answer;
import com.zgq.wokao.entity.paper.question.FillInQuestion;
import com.zgq.wokao.module.study.entity.StudyInfo;

import java.util.LinkedList;

public class FillInQuestionAdapter extends BaseViewPagerAdapter<FillInQuestion> {
    private LinkedList<View> mViewCache = new LinkedList<>();

    private View currentView = null;
    private int currentPosition = 0;


    private FillInQuestionViewHolder holder;

    public FillInQuestionAdapter(StudyInfo<FillInQuestion> studyInfo) {
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
        return getFillInQuestionView(container, position);
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

    public View getFillInQuestionView(ViewGroup container, int position) {
        FillInQuestionViewHolder fillInQuestionViewHolder = null;
        View convertView = null;
        if (mViewCache.size() == 0) {
            convertView = LayoutInflater.from(container.getContext())
                    .inflate(R.layout.viewadapter_fillinquestion_item, null, false);
            TextView questionBody = convertView.findViewById(R.id.fillinquestion_body);
            TextView questionAnswer = convertView.findViewById(R.id.fillinquestion_answer);
            fillInQuestionViewHolder = new FillInQuestionViewHolder();
            fillInQuestionViewHolder.questionBody = questionBody;
            fillInQuestionViewHolder.questionAnswer = questionAnswer;
            convertView.setTag(fillInQuestionViewHolder);
        } else {
            convertView = mViewCache.removeFirst();
            fillInQuestionViewHolder = (FillInQuestionViewHolder) convertView.getTag();
        }
        holder = fillInQuestionViewHolder;
        FillInQuestion question = studyInfo.getQuestions().get(position);
        fillInQuestionViewHolder.questionBody.setText("" + (position + 1) + ". " + question.getBody().getContent());
        if (studyInfo.hasAnswered(question.getId())) {
            fillInQuestionViewHolder.questionAnswer.setText(question.getAnswer().getContent());
        } else {
            fillInQuestionViewHolder.questionAnswer.setText("");
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

    @Override
    public boolean showCurrentAnswer() {
        FillInQuestion question = studyInfo.getQuestions().get(currentPosition);
        if (studyInfo.hasAnswered(question.getId())) return false;
        ((FillInQuestionViewHolder) (currentView.getTag())).questionAnswer.setText(question.getAnswer().getContent());
        studyInfo.saveMyAnswer(question.getId(),new Answer());
        //TODO 再次更新学习记录
//        getCorrectAnswer(getPaperId(), question);
        return true;
    }

    @Override
    public void hideCurrentAnswer() {

    }

    @Override
    public int getLastPosition() {
        return studyInfo.getQuestions().get(currentPosition).getInfo().getIndex() - 1;
    }

    public final class FillInQuestionViewHolder {
        public TextView questionBody;
        public TextView questionAnswer;
    }
}
