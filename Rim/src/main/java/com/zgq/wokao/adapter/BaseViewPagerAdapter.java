package com.zgq.wokao.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.zgq.wokao.entity.paper.question.IQuestion;
import com.zgq.wokao.module.study.entity.StudyInfo;

import java.util.List;

public abstract class BaseViewPagerAdapter<T extends IQuestion> extends PagerAdapter{
    private static final String TAG = BaseViewPagerAdapter.class.getSimpleName();

    protected StudyInfo<T> studyInfo;

    private OnStudiedListener studiedListener;

    public BaseViewPagerAdapter(List<T> questions){
        this.studyInfo = new StudyInfo<>();
        studyInfo.setQuestions(questions);
    }

    public void replaceData(List<T> questions){
        this.studyInfo.setQuestions(questions);
        notifyDataSetChanged();
    }

    protected void getFalseAnswer(String paperId, IQuestion question) {
        studiedListener.onFalse();
    }

    protected void getCorrectAnswer(String paperId, IQuestion question) {
        studiedListener.onCorrect();
    }

    public abstract int getLastPosition();

    public void setStudiedListener(OnStudiedListener listener) {
        this.studiedListener = listener;
    }

    public abstract View getCurrentView();

    public abstract int getCurrentPosition();

    public abstract boolean showCurrentAnswer();

    public abstract void hideCurrentAnswer();

    public interface OnStudiedListener {
        void onFalse();

        void onCorrect();
    }
}
