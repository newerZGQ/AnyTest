package com.zgq.wokaofree.action.viewdata;

import com.zgq.wokaofree.action.BaseAction;
import com.zgq.wokaofree.action.paper.impl.PaperAction;
import com.zgq.wokaofree.model.paper.IExamPaper;
import com.zgq.wokaofree.model.paper.QuestionType;
import com.zgq.wokaofree.model.paper.question.IQuestion;
import com.zgq.wokaofree.model.viewdate.QstData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zgq on 2017/3/19.
 */

public class ViewDataAction extends BaseAction implements IViewDataAction {
    public static class InstanceHolder {
        public static ViewDataAction instance = new ViewDataAction();
    }

    public static ViewDataAction getInstance() {
        return InstanceHolder.instance;
    }

    private ViewDataAction() {
    }

    @Override
    public ArrayList<QstData> getQstData(IExamPaper paper) {
        ArrayList<QstData> qstDatas = new ArrayList<>();
        ArrayList<IQuestion> questions = null;
        questions = PaperAction.getInstance().getQuestins(paper, QuestionType.FILLIN);
        if (questions.size() != 0){
            qstDatas.add(QstData.Formator.format(questions, paper.getPaperInfo()));
        }

        questions = PaperAction.getInstance().getQuestins(paper, QuestionType.TF);
        if (questions.size() != 0){
            qstDatas.add(QstData.Formator.format(questions, paper.getPaperInfo()));
        }

        questions = PaperAction.getInstance().getQuestins(paper, QuestionType.SINGLECHOOSE);
        if (questions.size() != 0){
            qstDatas.add(QstData.Formator.format(questions, paper.getPaperInfo()));
        }

        questions = PaperAction.getInstance().getQuestins(paper, QuestionType.MUTTICHOOSE);
        if (questions.size() != 0){
            qstDatas.add(QstData.Formator.format(questions, paper.getPaperInfo()));
        }

        questions = PaperAction.getInstance().getQuestins(paper, QuestionType.DISCUSS);
        if (questions.size() != 0){
            qstDatas.add(QstData.Formator.format(questions, paper.getPaperInfo()));
        }
        return qstDatas;
    }
}