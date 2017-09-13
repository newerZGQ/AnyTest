package com.zgq.wokao.ui.presenter.impl;

import com.zgq.wokao.action.paper.impl.StudySummaryAction;
import com.zgq.wokao.model.total.TotalDailyCount;
import com.zgq.wokao.ui.presenter.IHomePrerenter;
import com.zgq.wokao.ui.view.IHomeView;

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
