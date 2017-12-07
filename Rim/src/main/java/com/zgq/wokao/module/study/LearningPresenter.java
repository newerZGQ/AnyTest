package com.zgq.wokao.module.study;

import com.zgq.wokao.entity.paper.NormalExamPaper;
import com.zgq.wokao.entity.paper.question.Answer;
import com.zgq.wokao.entity.paper.question.IQuestion;
import com.zgq.wokao.entity.paper.question.QuestionType;
import com.zgq.wokao.module.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class LearningPresenter extends BasePresenter<StudyContract.LearningView>
        implements StudyContract.LearningPresenter{

    private NormalExamPaper normalExamPaper;

    private QuestionType currentQuestionType = QuestionType.FILLIN;

    private List<IQuestion> currentAllQuestions = new ArrayList<>();
    private ArrayList<Boolean> allAnsweredList = new ArrayList<>();
    private ArrayList<Answer> currentAllMyAnswer = new ArrayList<>();

    private ArrayList<IQuestion> currentStarQuestions = new ArrayList<>();
    private ArrayList<Boolean> starAnsweredList = new ArrayList<>();
    private ArrayList<Answer> currentStarMyAnswer = new ArrayList<>();

    @Inject
    public LearningPresenter(){

    }

    @Override
    public void initParams(String paperId, QuestionType type) {

    }

    @Override
    public void loadAllQuestions() {

    }

    @Override
    public void loadStarQuestions() {

    }
}
