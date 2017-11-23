package com.zgq.wokao.module.welcome;

import com.zgq.wokao.entity.summary.StudySummary;
import com.zgq.wokao.entity.summary.TotalDailyCount;
import com.zgq.wokao.module.base.BasePresenter;
import com.zgq.wokao.repository.RimRepository;
import com.zgq.wokao.util.DateUtil;

import javax.inject.Inject;

import io.realm.RealmList;

public class WelcomePresenter extends BasePresenter<WelcomeContract.MainView>
        implements WelcomeContract.MainPresenter {

    private RimRepository repository;

    @Inject
    public WelcomePresenter(RimRepository repository) {
        this.repository = repository;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        checkStudySummary();
    }

    private void checkStudySummary() {
        RealmList<TotalDailyCount> realmList = new RealmList<>();
        String today = DateUtil.getCurrentDate();
        for (int i = 0; i < 7; i++) {
            TotalDailyCount totalDailyCount = TotalDailyCount
                    .builder()
                    .date(DateUtil.getTargetDateApart(today, -i))
                    .build();
            realmList.add(0, totalDailyCount);
        }
        repository.saveSummary(StudySummary.builder()
                .lastWeekRecords(realmList)
                .build());
    }
}
