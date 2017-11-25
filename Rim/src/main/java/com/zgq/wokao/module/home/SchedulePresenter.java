package com.zgq.wokao.module.home;

import com.google.common.base.Optional;
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
    }

    @Inject
    public SchedulePresenter(RimRepository repository){
        this.repository = repository;
    }


    @Override
    public void loadSchedules() {
        repository.getAllExamPaperInfo()
                .subscribe(examPaperInfos -> {
                    if (examPaperInfos.size() != 0) {
                        view.setSchedulePapers(examPaperInfos);
                        updateDetail(0);
                    }else{
                        view.showEmptyView();
                    }
                });
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
}
