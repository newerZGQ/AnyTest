package com.zgq.wokao.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.util.Log;

import com.zgq.wokao.Util.LogUtil;
import com.zgq.wokao.action.paper.impl.PaperAction;
import com.zgq.wokao.model.paper.question.IQuestion;

/**
 * Created by zgq on 2017/4/19.
 */

public abstract class BaseViewPagerAdapter extends PagerAdapter implements BaseStudySystemAdapter {
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
    private PaperAction paperAction = PaperAction.getInstance();

    private void updateQstStudyInfo(String paperId, IQuestion question, boolean isCorrect) {
        paperAction.updateAllStudyInfo(paperId, question, isCorrect);
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
