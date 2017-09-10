package com.zgq.wokao.model.total;

import com.zgq.wokao.Util.DateUtil;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by zhangguoqiang on 2017/4/9.
 */

public class StudySummary extends RealmObject implements IStudySummary {
    private int studyCount;
    private int correctCount;
    private RealmList<TotalDailyCount> dailyCountRecords = new RealmList<>();

    public int getStudyCount() {
        return studyCount;
    }

    public void setStudyCount(int studyCount) {
        this.studyCount = studyCount;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }

    public RealmList<TotalDailyCount> getDailyCountRecords() {
        return dailyCountRecords;
    }

    public void setDailyCountRecords(RealmList<TotalDailyCount> dailyCountRecords) {
        this.dailyCountRecords = dailyCountRecords;
    }

    @Override
    public void studyCountPlus_1() {
        studyCount++;
    }

    @Override
    public void correctCountPlus_1() {
        correctCount++;
    }

    @Override
    public void updateDailyCount() {
        getCurrentDailyCount().setDailyCount(getCurrentDailyCount().getDailyCount() + 1);
    }

    private void addRecord() {
        if (lastRecordIsCurrent()) {
            return;
        }
        TotalDailyCount dailyCount = new TotalDailyCount();
        dailyCount.setDailyCount(0);
        dailyCount.setDate(DateUtil.getFormatData("yyyy-MM-dd"));
    }

    @Override
    public TotalDailyCount getCurrentDailyCount() {
        if (!lastRecordIsCurrent() || dailyCountRecords.size() == 0) {
            addRecord();
        }
        return dailyCountRecords.last();
    }

    //判断最后一次记录是不是今天的
    private boolean lastRecordIsCurrent() {
        if (dailyCountRecords.size() == 0) {
            TotalDailyCount totalDailyCount = new TotalDailyCount();
            totalDailyCount.setDailyCount(0);
            totalDailyCount.setDate(DateUtil.getFormatData("yyyy-MM-dd"));
            dailyCountRecords.add(totalDailyCount);
            return true;
        }
        TotalDailyCount last = dailyCountRecords.get(dailyCountRecords.size() - 1);
        String currentData = DateUtil.getYYYY_MM_DD();
        if (last.getDate().equals(currentData)) {
            return true;
        }
        return false;
    }
}
