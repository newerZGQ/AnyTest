package com.zgq.wokao.ui.view;

/**
 * Created by zgq on 2017/3/4.
 */

public interface IHomeView {
    public void updateSlideUp();
    public void goSerach();
    public void showScheduleFragment();
    public void showPapersFragment();
    public void showFragmetn(String fragmentTag);
    public void showProgressBar();
    public void hideProgressBar();
    public void notifyDataChanged();
}
