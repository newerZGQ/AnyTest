package com.zgq.wokaofree.ui.view;

import com.zgq.wokaofree.model.paper.QuestionType;
import com.zgq.wokaofree.model.total.StudySummary;

/**
 * Created by zgq on 2017/3/4.
 */

public interface IHomeView {
    void changeSlideUpState();

    void goSearch();

    void showScheduleFragment();

    void showPapersFragment();

    void showQuestionsList(String paperId);

    void setViewPagerScrollble(boolean scrollble);

    void hideToolBar(int duration);

    void showToolBar(int duration);

    void animateToolbarLeft(int duration);

    void animateToolbarRight(int duration);

}
