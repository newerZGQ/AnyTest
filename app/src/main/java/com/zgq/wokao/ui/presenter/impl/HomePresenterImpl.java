package com.zgq.wokao.ui.presenter.impl;

import android.util.Log;

import com.zgq.wokao.Util.DateUtil;
import com.zgq.wokao.action.paper.IPaperAction;
import com.zgq.wokao.action.paper.impl.PaperAction;
import com.zgq.wokao.action.paper.impl.StudySummaryAction;
import com.zgq.wokao.action.parser.IParserAction;
import com.zgq.wokao.action.parser.ParserAction;
import com.zgq.wokao.model.total.StudySummary;
import com.zgq.wokao.model.total.TotalDailyCount;
import com.zgq.wokao.ui.presenter.IHomePrerenter;
import com.zgq.wokao.ui.view.IHomeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.RealmList;

/**
 * Created by zgq on 2017/3/4.
 */

public class HomePresenterImpl implements IHomePrerenter{

    private IHomeView homeView;
    private StudySummaryAction summaryAction = StudySummaryAction.getInstance();
    private StudySummary studySummary = summaryAction.getStudySummary();
    public HomePresenterImpl(IHomeView homeView){
        this.homeView = homeView;
    }

    public void setSlideaMenuLayout(StudySummary studySummary){

    }

    public void updateSlideUp(){
        homeView.updateSlideUp();
    }
    public void goSearch(){
        homeView.goSearch();
    }
    public void showScheduleFragment(){
        homeView.showScheduleFragment();
    }
    public void showPapersFragment(){
        homeView.showPapersFragment();
    }

    private void initLineChart(StudySummary studySummary) {
        //x轴坐标对应的数据
        List<String> xValue = new ArrayList<>();
        //y轴坐标对应的数据
        List<Integer> yValue = new ArrayList<>();
        //折线对应的数据
        Map<String, Integer> value = new HashMap<>();

        RealmList<TotalDailyCount> list = studySummary.getDailyCountRecords();

        ArrayList<TotalDailyCount> data = new ArrayList<>();

        int count = 0;
        String dateTmp = "";
        String dateReal = DateUtil.getFormatData("yyyy-MM-dd");
        for (int i = list.size()-1; i< list.size(); i--){
            dateTmp = list.get(i).getDate();
            if (dateReal.equals(dateTmp)){
                data.add(list.get(i));
            }
        }

        for (int i = 0; i < 9; i++) {
            if (i == 0 || i == 1) {
                xValue.add((i + 1) + "月");
                value.put((i + 1) + "月", (int) (181));//60--240
                continue;
            }
            if (i == 7 || i == 8) {
                xValue.add((i + 1) + "月");
                value.put((i + 1) + "月", (int) (160));//60--240
                continue;
            }
            xValue.add((i + 1) + "月");
            value.put((i + 1) + "月", (int) (Math.random() * 181 + 60));//60--240
        }

        for (int i = 0; i < 9; i++) {
            yValue.add(i * 60);
        }
    }
}
