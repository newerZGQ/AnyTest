package com.zgq.wokao.module.study;

import com.zgq.wokao.entity.paper.NormalExamPaper;
import com.zgq.wokao.entity.paper.question.IQuestion;
import com.zgq.wokao.entity.paper.question.QuestionType;
import com.zgq.wokao.module.base.BasePresenter;
import com.zgq.wokao.repository.RimRepository;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class LearningPresenter extends BasePresenter<StudyContract.LearningView>
        implements StudyContract.LearningPresenter {

    private NormalExamPaper normalExamPaper;

    private QuestionType questionType;

    private RimRepository repository;

    private ArrayList<IQuestion> questions = new ArrayList<>();

    private HashMap<IQuestion, Boolean> answered;

    @Inject
    public LearningPresenter(RimRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initParams(String paperId, QuestionType type) {
        repository.queryPaper(paperId).subscribe(paperOptional -> this.normalExamPaper = paperOptional.get());
        questionType = type;

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
                .subscribe(question -> answered.put(question,false));
    }

    @Override
    public void subscribe() {
        super.subscribe();
        if (questionType == QuestionType.TF || questionType == QuestionType.SINGLECHOOSE) {
            view.hideAnswerBtn();
        }
        loadAllQuestions();
    }

    @Override
    public void loadAllQuestions() {
        loadQuestions(false);
    }

    @Override
    public void loadStarQuestions() {
        loadQuestions(true);
    }

    private void loadQuestions(boolean onlyStared){
        ArrayList<IQuestion> targetQuestions = new ArrayList<>();
        Flowable.fromIterable(questions)
                .filter(question -> !onlyStared || question.getInfo().isStared())
                .subscribe(question -> targetQuestions.add(question));
        view.showQuestions(targetQuestions);
        view.showQuestionIndex(targetQuestions, answered);
    }

    @Override
    public void updateStudiedRecord(IQuestion question, boolean correct){
        updateQuestionIndex(question);
        view.setAnswerState(true);
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

    private void updateQuestionIndex(IQuestion question){
        answered.put(question,true);
        view.notifyQuestionIndexChanged();
    }
}
