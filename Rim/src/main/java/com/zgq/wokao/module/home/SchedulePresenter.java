package com.zgq.wokao.module.home;

import com.google.common.base.Optional;
import com.orhanobut.logger.Logger;
import com.zgq.wokao.module.base.BasePresenter;
import com.zgq.wokao.repository.RimRepository;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class SchedulePresenter extends BasePresenter<HomeContract.ScheduleView>
        implements HomeContract.SchedulePresenter {

    private RimRepository repository;

    @Override
    public void subscribe() {
        super.subscribe();
        loadSchedules();
        updateDetail(0);
    }

    @Inject
    public SchedulePresenter(RimRepository repository){
        this.repository = repository;
    }


    @Override
    public void loadSchedules() {
        repository.getAllExamPaperInfo()
                .subscribe(examPaperInfos -> view.setSchedulePapers(examPaperInfos));
    }

    @Override
    public void updateDetail(int index) {
        repository.getAllExamPaperInfo()
                .flatMap(s -> {
                    Logger.d("exam " + s.get(index).getTitle());
                    Logger.d("exam " + s.get(index).getSchedule().getDailyRecords());
                    return Flowable.just(Optional.fromNullable(s.get(index).getSchedule()));
                })
                .subscribe(schedule -> {
                    if (schedule.isPresent()){
                        view.setDetail(schedule.get());
                    }
                });
    }
}
