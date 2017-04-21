package com.zgq.wokao.ui.presenter;

/**
 * Created by zgq on 2017/3/5.
 */

public interface IPapersPresenter {
    public void notifyDataChanged();
    public void initPapersList();
    public void deletePaper(String paperId);
    public void addToSchedule(String paperId);
    public void removeFromSchedule(String paperId);
    public int checkPapersSize();
}
