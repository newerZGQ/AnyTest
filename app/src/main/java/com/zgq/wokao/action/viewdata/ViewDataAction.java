package com.zgq.wokao.action.viewdata;

import com.zgq.wokao.action.BaseAction;
import com.zgq.wokao.action.paper.impl.PaperAction;
import com.zgq.wokao.model.paper.IExamPaper;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.paper.question.IQuestion;
import com.zgq.wokao.model.viewdate.QstData;

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