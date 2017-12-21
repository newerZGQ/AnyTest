package com.zgq.wokao.module.home;

import com.google.common.base.Optional;
import com.zgq.wokao.entity.paper.info.ExamPaperInfo;
import com.zgq.wokao.module.base.BasePresenter;
import com.zgq.wokao.repository.RimRepository;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.realm.RealmResults;

public class SchedulePresenter extends BasePresenter<HomeContract.ScheduleView>
        implements HomeContract.SchedulePresenter {

    private RimRepository repository;
    private ArrayList<ExamPaperInfo> paperInfos = new ArrayList<>();

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
        paperInfos.clear();
        if (forceUpdate) {
            repository.getAllExamPaperInfo()
                    .subscribe(examPaperInfos -> {
                        for (ExamPaperInfo paperInfo: examPaperInfos) {
                            if (paperInfo.getSchedule().isInSked()){
                                paperInfos.add(paperInfo);
                            }
                        }
                        view.setSchedulePapers(paperInfos);
                        if (paperInfos.size() > 0){
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
        view.setDetail(paperInfos.get(index).getSchedule());
    }

    @Override
    public void updateTask(int index, int task) {
        Disposable disposable = repository.copyFromRealm(paperInfos.get(index).getSchedule())
                .subscribe(schedule -> {
                    schedule.setDailyTask(task);
                    repository.copyToRealmOrUpdate(schedule);
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void loadQuestions(int position) {
        view.startQuestionsActivity(paperInfos.get(position).getId());
    }
}
