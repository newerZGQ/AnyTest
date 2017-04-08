package com.zgq.wokao.ui.view;

/**
 * Created by zgq on 2017/3/4.
 */

public interface IHomeView {
    public void updateSlideUp();
    public void goSearch();
    public void showScheduleFragment();
    public void showPapersFragment();
    public void showFragment(String fragmentTag);
    public void showProgressBar();
    public void hideProgressBar();
    public void notifyDataChanged();
    public void hideLoadingView();
    public void showLoadingView();
    public void setNeedUpdateData(boolean needUpdateData);
}
