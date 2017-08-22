package com.zgq.wokao.ui.view;

import com.zgq.wokao.model.total.StudySummary;

/**
 * Created by zgq on 2017/3/4.
 */

public interface IHomeView {
    void updateSlideUp();
    void goSearch();
    void showScheduleFragment();
    void showPapersFragment();
    void showQuestionsFragment(String paperId);
    void showFragment(String fragmentTag);
    void showProgressBar();
    void hideProgressBar();
    void notifyDataChanged();
    void hideLoadingView();
    void showLoadingView();
    void setNeedUpdateData(boolean needUpdateData);
    void setSlideaMenuLayout(StudySummary studySummary);
    void setViewPagerScrollble(boolean scrollble);
    void hideToolBar();
}
