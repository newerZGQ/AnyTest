package com.zgq.wokao.module.study;

import com.google.common.base.Strings;
import com.zgq.wokao.entity.paper.NormalExamPaper;
import com.zgq.wokao.entity.paper.info.DailyRecord;
import com.zgq.wokao.entity.paper.question.IQuestion;
import com.zgq.wokao.entity.paper.question.QuestionType;
import com.zgq.wokao.module.home.summary.StudySummary;
import com.zgq.wokao.module.home.summary.TotalDailyCount;
import com.zgq.wokao.module.base.BasePresenter;
import com.zgq.wokao.repository.RimRepository;
import com.zgq.wokao.common.utils.DateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.realm.RealmList;

public class LearningPresenter extends BasePresenter<StudyContract.LearningView>
        implements StudyContract.LearningPresenter {

    private NormalExamPaper normalExamPaper;

    private QuestionType questionType;

    private String initQuestionId;

    private RimRepository repository;

    private ArrayList<IQuestion> questions = new ArrayList<>();

    private HashMap<IQuestion, Boolean> answered;

    @Inject
    public LearningPresenter(RimRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initParams(String paperId, QuestionType type, String questionId) {
        repository.queryPaper(paperId).subscribe(paperOptional -> this.normalExamPaper = paperOptional.get());
        questionType = type;
        this.initQuestionId = questionId;

        switch (questionType) {
            case FILLIN:
                Flowable.fromIterable(normalExamPaper.getFillInQuestions())
                        .subscribe(question -> questions.add(question));
                break;
            case TF:
                Flowable.fromIterable(normalExamPaper.getTfQuestions())
                        .subscribe(question -> questions.add(question));
                break;
            case SINGLECHOOSE:
                Flowable.fromIterable(normalExamPaper.getSglChoQuestions())
                        .subscribe(question -> questions.add(question));
                break;
            case MUTTICHOOSE:
                Flowable.fromIterable(normalExamPaper.getMultChoQuestions())
                        .subscribe(question -> questions.add(question));
                break;
            case DISCUSS:
                Flowable.fromIterable(normalExamPaper.getDiscussQuestions())
                        .subscribe(question -> questions.add(question));
                break;
            default:
                break;
        }

        answered = new HashMap<>(questions.size());
        Flowable.fromIterable(questions)
                .subscribe(question -> answered.put(question, false));
    }

    @Override
    public void subscribe() {
        super.subscribe();
        if (questionType == QuestionType.TF || questionType == QuestionType.SINGLECHOOSE) {
            view.hideAnswerBtn();
        }
        loadAllQuestions();
        if (!Strings.isNullOrEmpty(initQuestionId)) {
            toTarget(initQuestionId);
        }
    }

    private void toTarget(String questionId){
        for (int i = 0; i < questions.size(); i++){
            if (questionId.equals(questions.get(i).getId())){
                view.showTargetQuestion(i);
            }
        }
    }

    @Override
    public void loadAllQuestions() {
        loadQuestions(false);
    }

    @Override
    public void loadStarQuestions() {
        loadQuestions(true);
    }

    private void loadQuestions(boolean onlyStared) {
        ArrayList<IQuestion> targetQuestions = new ArrayList<>();
        Flowable.fromIterable(questions)
                .filter(question -> !onlyStared || question.getInfo().isStared())
                .subscribe(question -> targetQuestions.add(question));
        view.showQuestions(targetQuestions);
        view.showQuestionIndex(targetQuestions, answered);
    }

    @Override
    public void updateStudiedRecord(IQuestion question, boolean correct) {
        updateQuestionIndex(question);
        view.setAnswerState(true);
        updateSummary(correct);
        updatePaperSchedule(question,correct);
    }

    @Override
    public void starQuestion(IQuestion question) {
        repository.starQuestion(question, !question.getInfo().isStared());
        view.setStarState(question.getInfo().isStared());
    }

    @Override
    public void onLoadQuestion(IQuestion question) {
        view.setStarState(question.getInfo().isStared());
        view.setAnswerState(answered.get(question));
    }

    private void updateQuestionIndex(IQuestion question) {
        answered.put(question, true);
        view.notifyQuestionIndexChanged();
    }

    private void updatePaperSchedule(IQuestion question, boolean correct){
        repository.copyFromRealm(normalExamPaper.getPaperInfo().getSchedule())
                .subscribe(schedule -> {
                   schedule.setTotalCount(schedule.getTotalCount() + 1);
                   schedule.setCorrectCount(correct? schedule.getCorrectCount() + 1 : schedule.getCorrectCount());
                   DailyRecord lastRecord = schedule.getDailyRecords().last();
                   if (lastRecord == null || lastRecord.getDate().equals(DateUtil.getCurrentDate())){
                       DailyRecord todayRecord = DailyRecord.builder()
                               .id(UUID.randomUUID().toString())
                               .date(DateUtil.getCurrentDate())
                               .studyCount(0)
                               .build();
                       schedule.getDailyRecords().add(todayRecord);
                   }else {
                       lastRecord.setStudyCount(lastRecord.getStudyCount() + 1);
                   }
                   schedule.setLastStudyType(question.getInfo().getType());
                   schedule.setLastStudyQuestionId(question.getId());
                   repository.copyToRealmOrUpdate(schedule);
                });
    }

    private void updateSummary(boolean correct) {
        final StudySummary[] tmp = new StudySummary[1];
        repository.getStudySummary().subscribe(studySummaryOptional -> {
            tmp[0] = studySummaryOptional.get();
        });

        repository.copyFromRealm(tmp[0])
                .subscribe(studySummary -> {
                    studySummary.setStudyCount(studySummary.getStudyCount() + 1);
                    if (correct) {
                        studySummary.setCorrectCount(studySummary.getCorrectCount() + 1);
                    }
                    RealmList<TotalDailyCount> lastWeekRecords = studySummary.getLastWeekRecords();
                    if (!lastRecordIsCurrent()) {
                        lastWeekRecords.remove(0);
                        TotalDailyCount totalDailyCount = TotalDailyCount.builder()
                                .dailyCount(0)
                                .date(DateUtil.getCurrentDate())
                                .id(UUID.randomUUID().toString())
                                .build();
                        lastWeekRecords.add(totalDailyCount);
                    }
                    String today = lastWeekRecords.last().getDate();
                    String dateToCheck = DateUtil.getTargetDateApart(today,-1);
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
                        dateToCheck = DateUtil.getTargetDateApart(dateToCheck,-1);
                    }

                    int todayCount = lastWeekRecords.last().getDailyCount();
                    lastWeekRecords.last().setDailyCount(todayCount + 1);

                    repository.copyToRealmOrUpdate(studySummary);
                });
    }

    private boolean lastRecordIsCurrent() {
        final boolean[] result = {false};
        repository.getStudySummary()
                .subscribe(studySummaryOptional -> {
                    StudySummary summary = studySummaryOptional.get();
                    RealmList<TotalDailyCount> lastWeekRecords = summary.getLastWeekRecords();
                    TotalDailyCount last = lastWeekRecords.get(lastWeekRecords.size() - 1);
                    String currentData = DateUtil.getCurrentDate();
                    if (last.getDate().equals(currentData)) {
                        result[0] = true;
                    }else {
                        result[0] = false;
                    }
                });
        return result[0];
    }
}
