package com.zgq.wokao.module.question;


import com.zgq.wokao.adapter.QuestionsInfoAdapter;
import com.zgq.wokao.entity.paper.info.ExamPaperInfo;
import com.zgq.wokao.entity.paper.question.DiscussQuestion;
import com.zgq.wokao.entity.paper.question.FillInQuestion;
import com.zgq.wokao.entity.paper.question.IQuestion;
import com.zgq.wokao.entity.paper.question.MultChoQuestion;
import com.zgq.wokao.entity.paper.question.QuestionType;
import com.zgq.wokao.entity.paper.question.SglChoQuestion;
import com.zgq.wokao.entity.paper.question.TFQuestion;
import com.zgq.wokao.module.base.BasePresenter;
import com.zgq.wokao.repository.RimRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class QuestionPresenter extends BasePresenter<QuestionContract.View> implements QuestionContract.Presenter {

    private RimRepository repository;
    private ArrayList<QuestionsInfoAdapter.QuestionsInfo> questionsInfos = new ArrayList<>();
    private ExamPaperInfo info;

    @Inject
    public QuestionPresenter(RimRepository repository){
        this.repository = repository;
    }

    @Override
    public void loadQuestions(String paperId) {
        questionsInfos.clear();
        repository.queryPaper(paperId)
                .subscribe(paper -> {
                    if (paper.isPresent()) {
                        info = paper.get().getPaperInfo();
                        questionsInfos.add(parseQuestionsInfo(info.getId(),paper.get().getFillInQuestions()));
                        questionsInfos.add(parseQuestionsInfo(info.getId(),paper.get().getTfQuestions()));
                        questionsInfos.add(parseQuestionsInfo(info.getId(),paper.get().getSglChoQuestions()));
                        questionsInfos.add(parseQuestionsInfo(info.getId(),paper.get().getMultChoQuestions()));
                        questionsInfos.add(parseQuestionsInfo(info.getId(),paper.get().getDiscussQuestions()));
                        view.showQuestions(questionsInfos,info);
                    }
                });
    }

    @Override
    public void loadStudyQuestions(int position) {
        view.startStudy(questionsInfos.get(position).getPaperId(),questionsInfos.get(position).getType());
    }

    private <T extends IQuestion> QuestionsInfoAdapter.QuestionsInfo parseQuestionsInfo(String paperId,
                                                                                        List<T> questions){
        if (questions.size() == 0){
            return null;
        }

        QuestionsInfoAdapter.QuestionsInfo info = QuestionsInfoAdapter.QuestionsInfo.builder()
                .paperId(paperId)
                .type(QuestionType.NOTQUESTION)
                .qstCount(0)
                .starCount(0)
                .build();

        if (questions.get(0) instanceof FillInQuestion){
            info.setType(QuestionType.FILLIN);
            for (T t: questions) {
                FillInQuestion tmp = (FillInQuestion)t;
                info.setQstCount(info.getQstCount() + 1);
                if (tmp.getInfo().isStared()){
                    info.setStarCount(info.getStarCount()+1);
                }
            }
            return info;
        }

        if (questions.get(0) instanceof TFQuestion){
            info.setType(QuestionType.TF);
            for (T t: questions) {
                TFQuestion tmp = (TFQuestion)t;
                info.setQstCount(info.getQstCount() + 1);
                if (tmp.getInfo().isStared()){
                    info.setStarCount(info.getStarCount()+1);
                }
            }
            return info;
        }

        if (questions.get(0) instanceof SglChoQuestion){
            info.setType(QuestionType.SINGLECHOOSE);
            for (T t: questions) {
                SglChoQuestion tmp = (SglChoQuestion)t;
                info.setQstCount(info.getQstCount() + 1);
                if (tmp.getInfo().isStared()){
                    info.setStarCount(info.getStarCount()+1);
                }
            }
            return info;
        }

        if (questions.get(0) instanceof MultChoQuestion){
            info.setType(QuestionType.MUTTICHOOSE);
            for (T t: questions) {
                MultChoQuestion tmp = (MultChoQuestion)t;
                info.setQstCount(info.getQstCount() + 1);
                if (tmp.getInfo().isStared()){
                    info.setStarCount(info.getStarCount()+1);
                }
            }
            return info;
        }

        if (questions.get(0) instanceof DiscussQuestion){
            info.setType(QuestionType.DISCUSS);
            for (T t: questions) {
                DiscussQuestion tmp = (DiscussQuestion)t;
                info.setQstCount(info.getQstCount() + 1);
                if (tmp.getInfo().isStared()){
                    info.setStarCount(info.getStarCount()+1);
                }
            }
            return info;
        }
        return null;
    }
}
