package com.zgq.wokao.action.viewdata;

import com.zgq.wokao.action.BaseAction;
import com.zgq.wokao.action.paper.impl.PaperAction;
import com.zgq.wokao.model.paper.IExamPaper;
import com.zgq.wokao.model.paper.NormalIExamPaper;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.paper.question.IQuestion;
import com.zgq.wokao.model.viewdate.QstData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zgq on 2017/3/19.
 */

public class ViewDataAction extends BaseAction implements IViewDataAction{
    public static class InstanceHolder{
        public static ViewDataAction instance = new ViewDataAction();
    }

    public static ViewDataAction getInstance(){
        return InstanceHolder.instance;
    }

    private ViewDataAction(){}
    @Override
    public ArrayList<QstData> getQstData(IExamPaper paper) {
        ArrayList<QstData> qstDatas = new ArrayList<>();
        qstDatas.add(QstData.Formator.format(PaperAction.getInstance().getQuestins(paper, QuestionType.fillin),paper.getPaperInfo()));
        qstDatas.add(QstData.Formator.format(PaperAction.getInstance().getQuestins(paper, QuestionType.tf),paper.getPaperInfo()));
        qstDatas.add(QstData.Formator.format(PaperAction.getInstance().getQuestins(paper, QuestionType.sglc),paper.getPaperInfo()));
        qstDatas.add(QstData.Formator.format(PaperAction.getInstance().getQuestins(paper, QuestionType.mtlc),paper.getPaperInfo()));
        qstDatas.add(QstData.Formator.format(PaperAction.getInstance().getQuestins(paper, QuestionType.disc),paper.getPaperInfo()));
        return qstDatas;
    }
}