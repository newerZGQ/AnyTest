package com.zgq.wokao.model.total;

import android.util.Log;

import com.zgq.wokao.Util.DateUtil;
import com.zgq.wokao.Util.ListUtil;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by zhangguoqiang on 2017/4/9.
 */

public class StudySummary extends RealmObject implements IStudySummary {
    private static String TAG = StudySummary.class.getSimpleName();
    private int studyCount;
    private int correctCount;
    private RealmList<TotalDailyCount> lastWeekRecords;

    @Override
    public int getStudyCount() {
        return studyCount;
    }

    @Override
    public int getCorrectCount() {
        return correctCount;
    }

    public List<TotalDailyCount> getLastWeekRecords() {
        return ListUtil.changeRealmListToList(lastWeekRecords);
    }

    @Override
    public void initSummary() {
        initLastWeekRecords();
    }

    @Override
    public void updateSummary(boolean isCorrect) {
        studyCount++;
        if (isCorrect) {
            correctCount++;
        }
        updateLastWeekRecords();
    }

    private void updateLastWeekRecords() {
        if (lastWeekRecords == null || lastWeekRecords.size() == 0) {
            initLastWeekRecords();
        }
        if (!lastRecordIsCurrent()) {
            addTodayRecord();
        }
        checkDateAvailiable();
        int todayCount = lastWeekRecords.last().getDailyCount();
        lastWeekRecords.last().setDailyCount(todayCount + 1);
    }

    private void initLastWeekRecords() {
        lastWeekRecords = new RealmList<>();
        String data = DateUtil.getYYYY_MM_DD();
        for (int i = 0; i < 7; i++) {
            TotalDailyCount totalDailyCount = new TotalDailyCount();
            totalDailyCount.setDate(data);
            totalDailyCount.setDailyCount(0);
            lastWeekRecords.add(i, totalDailyCount);
        }
        TotalDailyCount totalDailyCount;
        for (int i = 5; i >= 0; i--) {
            totalDailyCount = lastWeekRecords.get(i);
            data = DateUtil.getSpecifiedDayBefore(data);
            totalDailyCount.setDate(data);
        }
    }

    private void addTodayRecord() {
        lastWeekRecords.remove(0);
        TotalDailyCount totalDailyCount = new TotalDailyCount();
        totalDailyCount.setDate(DateUtil.getYYYY_MM_DD());
        totalDailyCount.setDailyCount(0);
        lastWeekRecords.add(totalDailyCount);
    }

    private void checkDateAvailiable() {
        String today = lastWeekRecords.last().getDate();
        String dateToCheck = DateUtil.getSpecifiedDayBefore(today);
        for (int i = lastWeekRecords.size() - 2; i >= 0; i--) {
            if (!lastWeekRecords.get(i).getDate().equals(dateToCheck)) {
                for (int j = 1; j <= i; j++) {
                    TotalDailyCount totalDailyCount = lastWeekRecords.get(j);
                    lastWeekRecords.set(j - 1, totalDailyCount);
                }
                TotalDailyCount totalDailyCount = new TotalDailyCount();
                totalDailyCount.setDate(dateToCheck);
                totalDailyCount.setDailyCount(0);
                lastWeekRecords.set(i, totalDailyCount);
            }
            dateToCheck = DateUtil.getSpecifiedDayBefore(dateToCheck);
        }
    }

    //判断最后一次记录是不是今天的
    private boolean lastRecordIsCurrent() {
        if (lastWeekRecords.size() == 0) {
            return false;
        }
        TotalDailyCount last = lastWeekRecords.get(lastWeekRecords.size() - 1);
        String currentData = DateUtil.getYYYY_MM_DD();
        if (last.getDate().equals(currentData)) {
            return true;
        }
        return false;
    }
}
