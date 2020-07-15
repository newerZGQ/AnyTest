package com.zgq.wokao.module.home;

import com.zgq.wokao.entity.paper.NormalExamPaper;
import com.zgq.wokao.entity.paper.info.ExamPaperInfo;
import com.zgq.wokao.entity.paper.question.QuestionType;
import com.zgq.wokao.module.base.BasePresenter;
import com.zgq.wokao.repository.RimRepository;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

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
        }else{
            view.hideEmptyView();
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
        repository.queryPaperByInfoId(paperInfos.get(position).getId())
                .subscribe(paperOptional -> {
                    if (paperOptional.isPresent()){
                        view.startQuestionsActivity(paperOptional.get().getId());
                    }
                });

    }

    @Override
    public void loadStudyInfo(String paperInfoId) {
        repository.queryPaperByInfoId(paperInfoId)
                .subscribe(paperOptional -> {
                    if (paperOptional.isPresent()){
                        NormalExamPaper paper = paperOptional.get();
                        QuestionType type = QuestionType
                                .parseFromValue(paper.getPaperInfo().getSchedule().getLastStudyType());
                        String questionId = paper.getPaperInfo().getSchedule().getLastStudyQuestionId();
                        view.startStudy(paper.getId(),type,questionId);
                    }
                });
    }
}
