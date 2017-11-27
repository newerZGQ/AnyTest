package com.zgq.wokao.module.home;

import com.google.common.base.Optional;
import com.google.common.eventbus.Subscribe;
import com.orhanobut.logger.Logger;
import com.zgq.wokao.entity.paper.info.ExamPaperInfo;
import com.zgq.wokao.entity.paper.info.Schedule;
import com.zgq.wokao.eventbus.EventBusCenter;
import com.zgq.wokao.module.base.BasePresenter;
import com.zgq.wokao.repository.RimRepository;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import io.reactivex.Flowable;
import io.realm.RealmResults;

public class SchedulePresenter extends BasePresenter<HomeContract.ScheduleView>
        implements HomeContract.SchedulePresenter {

    private RimRepository repository;
    private RealmResults<ExamPaperInfo> paperInfos;

    @Override
    public void subscribe() {
        super.subscribe();
        loadSchedules(true);
    }

    @Inject
    public SchedulePresenter(RimRepository repository){
        this.repository = repository;
    }

    @Override
    public void loadSchedules(boolean forceUpdate) {
        if (forceUpdate) {
            repository.getAllExamPaperInfo()
                    .subscribe(examPaperInfos -> {
                        paperInfos = examPaperInfos;
                        view.setSchedulePapers(paperInfos);
                        if (paperInfos.size() != 0) {
                            updateDetail(0);
                        }
                    });
        }else{
            view.notifyDataChanged();
        }
        if (paperInfos.size() == 0) {
            view.showEmptyView();
        }
    }

    @Override
    public void updateDetail(int index) {
        repository.getAllExamPaperInfo()
                .flatMap(s -> Flowable.just(Optional.fromNullable(s.get(index).getSchedule()))
                )
                .subscribe(schedule -> {
                    if (schedule.isPresent()){
                        view.setDetail(schedule.get());
                    }
                });
    }

    @Override
    public void updateTask(int index, int task) {
        repository.copyFromRealm(paperInfos.get(index).getSchedule())
                .subscribe(schedule -> {
                    schedule.setDailyTask(task);
                    repository.copyToRealmOrUpdate(schedule);
                });
        Logger.d("schedule " + paperInfos.get(index).getSchedule().getDailyTask());
    }
}
