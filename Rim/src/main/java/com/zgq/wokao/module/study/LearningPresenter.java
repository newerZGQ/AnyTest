package com.zgq.wokao.module.study;

import com.zgq.wokao.entity.paper.NormalExamPaper;
import com.zgq.wokao.entity.paper.question.Answer;
import com.zgq.wokao.entity.paper.question.FillInQuestion;
import com.zgq.wokao.entity.paper.question.IQuestion;
import com.zgq.wokao.entity.paper.question.QuestionType;
import com.zgq.wokao.module.base.BasePresenter;
import com.zgq.wokao.repository.RimRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import io.reactivex.Flowable;

public class LearningPresenter extends BasePresenter<StudyContract.LearningView>
        implements StudyContract.LearningPresenter {

    private NormalExamPaper normalExamPaper;

    private QuestionType questionType;

    private RimRepository repository;

    @Inject
    public LearningPresenter(RimRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initParams(String paperId, QuestionType type) {
        repository.queryPaper(paperId).subscribe(paperOptional -> this.normalExamPaper = paperOptional.get());
        questionType = type;
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
        ArrayList<IQuestion> questions = new ArrayList<>();
        switch (questionType) {
            case FILLIN:
                Flowable.fromIterable(normalExamPaper.getFillInQuestions())
                        .filter(question -> !onlyStared || question.getInfo().isStared())
                        .subscribe(question -> questions.add(question));
                break;
            case TF:
                Flowable.fromIterable(normalExamPaper.getTfQuestions())
                        .filter(question -> !onlyStared || question.getInfo().isStared())
                        .subscribe(question -> questions.add(question));
                break;
            case SINGLECHOOSE:
                Flowable.fromIterable(normalExamPaper.getSglChoQuestions())
                        .filter(question -> !onlyStared || question.getInfo().isStared())
                        .subscribe(question -> questions.add(question));
                break;
            case MUTTICHOOSE:
                Flowable.fromIterable(normalExamPaper.getMultChoQuestions())
                        .filter(question -> !onlyStared || question.getInfo().isStared())
                        .subscribe(question -> questions.add(question));
                break;
            case DISCUSS:
                Flowable.fromIterable(normalExamPaper.getDiscussQuestions())
                        .filter(question -> !onlyStared || question.getInfo().isStared())
                        .subscribe(question -> questions.add(question));
                break;
            default:
                break;
        }
        view.showQuestions(questions);
    }
}
