package com.zgq.wokao.adapter;

import android.support.v4.view.PagerAdapter;

import com.zgq.wokao.entity.paper.question.IQuestion;
import com.zgq.wokao.module.study.entity.StudyInfo;

public abstract class BaseViewPagerAdapter<T extends IQuestion> extends PagerAdapter implements StudySystemAdapter {
    private static final String TAG = BaseViewPagerAdapter.class.getSimpleName();

    protected StudyInfo<T> studyInfo;

    private OnStudiedListener studiedListener;

    public BaseViewPagerAdapter(StudyInfo<T> studyInfo){
        this.studyInfo = studyInfo;
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

    public interface OnStudiedListener {
        void onFalse();

        void onCorrect();
    }
}
