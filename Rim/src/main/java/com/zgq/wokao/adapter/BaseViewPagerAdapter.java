package com.zgq.wokao.adapter;

import android.support.v4.view.PagerAdapter;

import com.zgq.wokao.entity.paper.question.IQuestion;

public abstract class BaseViewPagerAdapter extends PagerAdapter implements StudySystemAdapter {
    private static final String TAG = BaseViewPagerAdapter.class.getSimpleName();

    private OnStudiedListener studiedListener;

    @Override
    public String getPaperId() {
        return paperId;
    }

    @Override
    public void setPaperId(String paperid) {
        this.paperId = paperid;
    }

    private String paperId;

    private void updateQstStudyInfo(String paperId, IQuestion question, boolean isCorrect) {
        //paperAction.updateAllStudyInfo(paperId, question, isCorrect);
    }

    protected void getFalseAnswer(String paperId, IQuestion question) {
        updateQstStudyInfo(paperId, question, false);
        if (studiedListener != null) {
            studiedListener.onFalse();
        }
    }

    protected void getCorrectAnswer(String paperId, IQuestion question) {
        updateQstStudyInfo(paperId, question, true);
        if (studiedListener != null) {
            studiedListener.onCorrect();
        }
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
