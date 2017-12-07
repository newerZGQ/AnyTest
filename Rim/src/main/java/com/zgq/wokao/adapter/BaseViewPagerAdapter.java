package com.zgq.wokao.adapter;

import android.support.v4.view.PagerAdapter;

import com.zgq.wokao.entity.paper.question.IQuestion;

public abstract class BaseViewPagerAdapter extends PagerAdapter implements BaseStudySystemAdapter {
    private static final String TAG = BaseViewPagerAdapter.class.getSimpleName();

    protected OnStudiedListener studiedListener = new OnStudiedListener() {
        @Override
        public void onFalse(int position,IQuestion question) {

        }

        @Override
        public void onCorrect(int position,IQuestion question) {

        }
    };

    public interface OnStudiedListener {
        void onFalse(int position,IQuestion question);

        void onCorrect(int position,IQuestion question);
    }
}
