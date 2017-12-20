package com.zgq.wokao.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.zgq.wokao.entity.paper.question.IQuestion;
import com.zgq.wokao.module.study.entity.StudyInfo;

import java.util.LinkedList;
import java.util.List;

public abstract class BaseViewPagerAdapter<T extends IQuestion> extends PagerAdapter{
    private static final String TAG = BaseViewPagerAdapter.class.getSimpleName();

    protected StudyInfo<T> studyInfo;
    protected OnStudiedListener studiedListener;
    protected View currentView = null;
    protected int currentPosition = 0;

    protected Context context;

    protected LinkedList<View> mViewCache = new LinkedList<>();

    public BaseViewPagerAdapter(List<T> questions, OnStudiedListener listener){
        this.studyInfo = new StudyInfo<>();
        studyInfo.setQuestions(questions);
        studiedListener = listener;
    }

    public void replaceData(List<T> questions){
        this.studyInfo.setQuestions(questions);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.studyInfo.getQuestions().size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        context = container.getContext();
        return container;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        currentView = (View) object;
        currentPosition = position;
        if (studyInfo.getQuestions().size() > 0) {
            studiedListener.onSelected(studyInfo.getQuestions().get(currentPosition), position);
        }
    }

    public abstract void showCurrentAnswer();

    public abstract void starCurrentQuestion();

    public interface OnStudiedListener {
        void onStudied(IQuestion question, boolean correct);
        void starQuestion(IQuestion question);
        void onSelected(IQuestion question, int position);
    }
}
