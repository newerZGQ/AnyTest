package com.zgq.wokao.action.paper.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.zgq.wokao.Util.LogUtil;
import com.zgq.wokao.action.BaseAction;
import com.zgq.wokao.action.paper.IPaperAction;
import com.zgq.wokao.action.paper.IQuestionAction;
import com.zgq.wokao.data.realm.Paper.impl.PaperDaoImpl;
import com.zgq.wokao.data.realm.Paper.impl.QuestionDaoImpl;
import com.zgq.wokao.exception.ParseException;
import com.zgq.wokao.model.paper.IExamPaper;
import com.zgq.wokao.model.paper.NormalExamPaper;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.paper.info.IPaperInfo;
import com.zgq.wokao.model.paper.question.IQuestion;
import com.zgq.wokao.model.paper.question.impl.DiscussQuestion;
import com.zgq.wokao.model.paper.question.impl.FillInQuestion;
import com.zgq.wokao.model.paper.question.impl.MultChoQuestion;
import com.zgq.wokao.model.paper.question.impl.SglChoQuestion;
import com.zgq.wokao.model.paper.question.impl.TFQuestion;
import com.zgq.wokao.parser.ParserHelper;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zgq on 2017/2/28.
 */

public class PaperAction extends BaseAction implements IPaperAction, IQuestionAction {

    private static final String TAG = PaperAction.class.getSimpleName();

    private PaperDaoImpl paperDao = PaperDaoImpl.getInstance();
    private QuestionDaoImpl questionDao = QuestionDaoImpl.getInstance();
    private ParserHelper parserHelper = ParserHelper.getInstance();
    private StudySummaryAction summaryAction = StudySummaryAction.getInstance();

    private PaperAction() {
    }

    @Override
    public ArrayList<IQuestion> getQuestins(IExamPaper paper, QuestionType type) {
        ArrayList<IQuestion> results = new ArrayList<>();
        switch (type) {
            case FILLIN:
                if (paper.getFillInQuestions() == null) {
                    break;
                }
                for (FillInQuestion question : paper.getFillInQuestions()) {
                    results.add(question);
                }
                break;
            case TF:
                if (paper.getTfQuestions() == null) {
                    break;
                }
                for (TFQuestion question : paper.getTfQuestions()) {
                    results.add(question);
                }
                break;
            case SINGLECHOOSE:
                if (paper.getSglChoQuestions() == null) {
                    break;
                }
                for (SglChoQuestion question : paper.getSglChoQuestions()) {
                    results.add(question);
                }
                break;
            case MUTTICHOOSE:
                if (paper.getMultChoQuestions() == null) {
                    break;
                }
                for (MultChoQuestion question : paper.getMultChoQuestions()) {
                    results.add(question);
                }
                break;
            case DISCUSS:
                if (paper.getDiscussQuestions() == null) {
                    break;
                }
                for (DiscussQuestion question : paper.getDiscussQuestions()) {
                    results.add(question);
                }
                break;
            default:
                break;
        }
        return results;
    }

    @Override
    public void updateQuestion(String questionId, IQuestion question) {
        questionDao.updateQuestion(questionId, question);
    }

    @Override
    public IQuestion queryQuestionById(String questionId, QuestionType type) {
        return questionDao.queryQuestionById(questionId, type);
    }


    public static class InstanceHolder {
        public static PaperAction instance = new PaperAction();
    }

    public static PaperAction getInstance() {
        return InstanceHolder.instance;
    }

    public void setAllPaperInSche() {
        List<NormalExamPaper> papers = getAllPaper();
        for (NormalExamPaper paper : papers) {
            paperDao.addToSchedule(paper);
            paperDao.openSchedule(paper);
        }
    }

    @Override
    public List<IPaperInfo> getAllPaperInfo() {
        return paperDao.getAllPaperInfo();
    }

    @Override
    public List<IPaperInfo> getPaperInfosInSchdl() {
        return paperDao.getPaperInfosInSchdl();
    }

    @Override
    public void deletePaperInfo(IPaperInfo paperInfo) {

    }

    @Override
    public void star(final IExamPaper paper) {
        paperDao.star(paper);
    }

    @Override
    public void star(String paperId) {
        NormalExamPaper paper = paperDao.query(paperId);
        star(paper);
    }

    @Override
    public void unStar(final IExamPaper paper) {
        paperDao.unStar(paper);
    }

    @Override
    public void unstar(String paperId) {
        NormalExamPaper paper = paperDao.query(paperId);
        unStar(paper);
    }

    @Override
    public void setTitle(IExamPaper paper, String title) {

    }

    @Override
    public void addToSchedule(final IExamPaper paper) {
        paperDao.addToSchedule(paper);
    }

    @Override
    public void removeFromSchedule(final IExamPaper paper) {
        paperDao.removeFromSchedule(paper);
    }

    @Override
    public void addToSchedule(String paperId) {
        IExamPaper paper = queryById(paperId);
        addToSchedule(paper);
    }

    @Override
    public void removeFromSchedule(String paperId) {
        IExamPaper paper = queryById(paperId);
        removeFromSchedule(paper);
    }

    @Override
    public void setLastStudyDate(IExamPaper paper) {
        paperDao.setLastStudyDate(paper);
    }

    @Override
    public void openSchedule(final IExamPaper paper) {
        paperDao.openSchedule(paper);
    }

    @Override
    public void closeSchedule(final IExamPaper paper) {
        paperDao.closeSchedule(paper);
    }

    @Override
    public void setDailyCount(final IExamPaper paper, final int count) {
        paperDao.setDailyCount(paper, count);
    }

    @Override
    public void updateDailyRecord(final IExamPaper paper) {
        paperDao.updateDailyRecord(paper);
    }

    @Override
    public void updateLastStudyPosition(@NonNull IExamPaper paper, @NonNull QuestionType type, @NonNull int position) {
        paperDao.setLastStudyInfo(paper.getPaperInfo().getId(), type, position);
    }

    @Override
    public List<NormalExamPaper> getAllPaper() {
        return paperDao.getAllPaper();
    }

    @Override
    public List<NormalExamPaper> getAllPaperInSchdl() {
        return paperDao.getAllPaperInSchdl();
    }

    @Override
    public void addExamPaper(IExamPaper paper) {
        paperDao.save((NormalExamPaper) paper);
    }

    @Override
    public void deleteExamPaper(IExamPaper paper) {
        paperDao.deleteById(paper.getPaperInfo().getId());
    }

    @Override
    public void deleteExamPaper(String paperId) {
        paperDao.deleteById(paperId);
    }

    @Override
    public IExamPaper queryById(String id) {
        return paperDao.query(id);
    }

    @Override
    public IExamPaper parseAndSave(String filePath) throws FileNotFoundException, ParseException {
        NormalExamPaper paper = parserHelper.parse(filePath);
        addExamPaper(paper);
        return paper;
    }

    @Override
    public IExamPaper parseAndSave(InputStream inputStream) throws ParseException {
        NormalExamPaper paper = parserHelper.parse(inputStream);
        addExamPaper(paper);
        return paper;
    }

    @Override
    public float getTotalAccuracy(IExamPaper paper) {
        float accuracy = 0f;
        int studyCount = 0;
        int correctCount = 0;
        for (IQuestion question : getQuestins(paper, QuestionType.FILLIN)) {
            studyCount += question.getRecord().getStudyNumber();
            correctCount += question.getRecord().getCorrectNumber();
        }
        for (IQuestion question : getQuestins(paper, QuestionType.TF)) {
            studyCount += question.getRecord().getStudyNumber();
            correctCount += question.getRecord().getCorrectNumber();
        }
        for (IQuestion question : getQuestins(paper, QuestionType.SINGLECHOOSE)) {
            studyCount += question.getRecord().getStudyNumber();
            correctCount += question.getRecord().getCorrectNumber();
        }
        for (IQuestion question : getQuestins(paper, QuestionType.MUTTICHOOSE)) {
            studyCount += question.getRecord().getStudyNumber();
            correctCount += question.getRecord().getCorrectNumber();
        }
        for (IQuestion question : getQuestins(paper, QuestionType.DISCUSS)) {
            studyCount += question.getRecord().getStudyNumber();
            correctCount += question.getRecord().getCorrectNumber();
        }
        if (studyCount == 0) return 0f;
        return correctCount / studyCount;
    }

    @Override
    public void updateAllStudyInfo(String paperId, IQuestion question, boolean isCorrect) {
        //更新某一题的记录
        updateQuestionRecord(question, isCorrect);
        //更新试卷每日的记录
        IExamPaper paper = queryById(paperId);
        updateDailyRecord(paper);
        //更新该试卷总的记录
        paperDao.updateStudyInfo(paper, isCorrect);
        //更新所有试卷学习记录总结
        summaryAction.updateSummary(question.getInfo().getType(), isCorrect);
    }

    @Override
    public void star(final IQuestion question) {
        questionDao.star(question);
    }

    @Override
    public void unStar(final IQuestion question) {
        questionDao.unStar(question);
    }

    @Override
    public void updateQuestionRecord(final IQuestion question, final boolean isCorrect) {
        questionDao.updateQuestionRecord(question, isCorrect);
    }
}
