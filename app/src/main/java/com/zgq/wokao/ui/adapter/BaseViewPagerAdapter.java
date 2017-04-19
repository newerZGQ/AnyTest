package com.zgq.wokao.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.util.Log;

import com.zgq.wokao.Util.LogUtil;
import com.zgq.wokao.action.paper.impl.PaperAction;
import com.zgq.wokao.model.paper.question.IQuestion;

/**
 * Created by zgq on 2017/4/19.
 */

public abstract class BaseViewPagerAdapter extends PagerAdapter implements BaseStudySystemAdapter{
    private static final String TAG = BaseViewPagerAdapter.class.getSimpleName();
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
    public void updateQstStudyInfo(String paperId,IQuestion question, boolean isCorrect){
        Log.d(LogUtil.PREFIX,TAG +" "+ paperId);
        paperAction.updateAllStudyInfo(paperId,question,isCorrect);
    }
}
