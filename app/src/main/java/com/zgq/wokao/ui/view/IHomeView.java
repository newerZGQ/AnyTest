package com.zgq.wokao.ui.view;

import com.zgq.wokao.model.paper.QuestionType;
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

    void setSlideaMenuLayout(StudySummary studySummary);

    void setViewPagerScrollble(boolean scrollble);

    void hideToolBar(int duration);

    void showToolBar(int duration);

}
