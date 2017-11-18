package com.zgq.wokaofree.ui.presenter.impl;

import com.zgq.wokaofree.action.paper.impl.StudySummaryAction;
import com.zgq.wokaofree.model.total.TotalDailyCount;
import com.zgq.wokaofree.ui.presenter.IHomePrerenter;
import com.zgq.wokaofree.ui.view.IHomeView;

import java.util.List;

/**
 * Created by zgq on 2017/3/4.
 */

public class HomePresenterImpl implements IHomePrerenter {

    private IHomeView homeView;

    public HomePresenterImpl(IHomeView homeView) {
        this.homeView = homeView;
    }

    @Override
    public List<TotalDailyCount> getDailyRecords() {
        return StudySummaryAction.getInstance().getStudySummary().getLastWeekRecords();
    }
}
