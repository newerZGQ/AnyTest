package com.zgq.wokao.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.zgq.wokao.entity.paper.question.IQuestion;
import com.zgq.wokao.module.study.entity.StudyInfo;

import java.util.List;

public abstract class BaseViewPagerAdapter<T extends IQuestion> extends PagerAdapter{
    private static final String TAG = BaseViewPagerAdapter.class.getSimpleName();

    protected StudyInfo<T> studyInfo;

    protected OnStudiedListener studiedListener;

    public BaseViewPagerAdapter(List<T> questions, OnStudiedListener listener){
        this.studyInfo = new StudyInfo<>();
        studyInfo.setQuestions(questions);
        studiedListener = listener;
    }

    public void replaceData(List<T> questions){
        this.studyInfo.setQuestions(questions);
        notifyDataSetChanged();
    }

    public abstract int getLastPosition();

    public void setStudiedListener(OnStudiedListener listener) {
        this.studiedListener = listener;
    }

    public abstract View getCurrentView();

    public abstract int getCurrentPosition();

    public abstract boolean showCurrentAnswer();

    public abstract void starCurrentQuestion();

    public abstract void hideCurrentAnswer();

    public interface OnStudiedListener {
        void onStudied(IQuestion question, boolean correct);
        void starQuestion(IQuestion question);
        void onSelected(IQuestion question, int position);
    }
}
