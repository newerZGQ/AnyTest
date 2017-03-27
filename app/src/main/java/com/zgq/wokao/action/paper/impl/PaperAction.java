package com.zgq.wokao.action.paper.impl;

import android.support.v4.view.PagerAdapter;

import com.zgq.wokao.action.BaseAction;
import com.zgq.wokao.action.paper.IPaperAction;
import com.zgq.wokao.action.paper.IQuestionAction;
import com.zgq.wokao.data.realm.Paper.impl.PaperDaoImpl;
import com.zgq.wokao.data.realm.Paper.impl.QuestionDaoImpl;
import com.zgq.wokao.exception.ParseException;
import com.zgq.wokao.model.paper.IExamPaper;
import com.zgq.wokao.model.paper.NormalIExamPaper;
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

import io.realm.Realm;

/**
 * Created by zgq on 2017/2/28.
 */

public class PaperAction extends BaseAction implements IPaperAction,IQuestionAction {

    private PaperDaoImpl paperDao = PaperDaoImpl.getInstance();
    private QuestionDaoImpl questionDao = QuestionDaoImpl.getInstance();
    private ParserHelper parserHelper = ParserHelper.getInstance();

    private PaperAction(){}

    @Override
    public ArrayList<IQuestion> getQuestins(IExamPaper paper, QuestionType type) {
        int typeIndex = type.getIndex();
        ArrayList<IQuestion> results = new ArrayList<>();
        switch (typeIndex){
            case QuestionType.fillin_index:
                for (FillInQuestion question : paper.getFillInQuestions()){
                    results.add((IQuestion)question);
                }
                break;
            case QuestionType.tf_index:
                for (TFQuestion question : paper.getTfQuestions()){
                    results.add((IQuestion)question);
                }
                break;
            case QuestionType.sglc_index:
                for (SglChoQuestion question : paper.getSglChoQuestions()){
                    results.add((IQuestion)question);
                }
                break;
            case QuestionType.mtlc_index:
                for (MultChoQuestion question : paper.getMultChoQuestions()){
                    results.add((IQuestion)question);
                }
                break;
            case QuestionType.disc_index:
                for (DiscussQuestion question : paper.getDiscussQuestions()){
                    results.add((IQuestion)question);
                }
                break;
            default:
                break;
        }
        return results;
    }


    public static class InstanceHolder{
        public static PaperAction instance = new PaperAction();
    }

    public static PaperAction getInstance(){
        return InstanceHolder.instance;
    }

    public void setAllPaperInSche(){
        List<NormalIExamPaper> papers = getAllPaper();
        for (NormalIExamPaper paper: papers){
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
    public void star(final IExamPaper paper) {
        paperDao.star(paper);
    }

    @Override
    public void star(String paperId) {
        NormalIExamPaper paper = paperDao.query(paperId);
        star(paper);
    }

    @Override
    public void unStar(final IExamPaper paper) {
        paperDao.unStar(paper);
    }

    @Override
    public void unstar(String paperId) {
        NormalIExamPaper paper = paperDao.query(paperId);
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
    public void setLastStudyDate(IExamPaper paper) {


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
        paperDao.setDailyCount(paper,count);
    }

    @Override
    public void updateDailyRecord(final IExamPaper paper) {
        paperDao.updateDailyRecord(paper);
    }

    @Override
    public List<NormalIExamPaper> getAllPaper() {
        return paperDao.getAllPaper();
    }

    @Override
    public List<NormalIExamPaper> getAllPaperInSchdl() {
        return paperDao.getAllPaperInSchdl();
    }

    @Override
    public void addExamPaper(IExamPaper paper) {
        paperDao.save((NormalIExamPaper) paper);
    }

    @Override
    public void deleteExamPaper(IExamPaper paper) {
        paperDao.delete((NormalIExamPaper) paper);
    }

    @Override
    public IExamPaper queryById(String id) {
        return paperDao.query(id);
    }

    @Override
    public IExamPaper parseAndSave(String filePath) throws FileNotFoundException, ParseException {
        NormalIExamPaper paper =  parserHelper.parse(filePath);
        addExamPaper(paper);
        return paper;
    }

    @Override
    public IExamPaper parseAndSave(InputStream inputStream) throws ParseException {
        NormalIExamPaper paper =  parserHelper.parse(inputStream);
        addExamPaper(paper);
        return paper;
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
        questionDao.updateQuestionRecord(question,isCorrect);
    }
}
