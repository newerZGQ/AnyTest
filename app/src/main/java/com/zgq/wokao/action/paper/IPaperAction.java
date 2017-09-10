package com.zgq.wokao.action.paper;

import com.zgq.wokao.exception.ParseException;
import com.zgq.wokao.model.paper.IExamPaper;
import com.zgq.wokao.model.paper.NormalExamPaper;
import com.zgq.wokao.model.paper.question.IQuestion;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by zgq on 2017/2/28.
 */

public interface IPaperAction extends IPaperInfoAction, IPaperSchdlAction {
    public List<NormalExamPaper> getAllPaper();

    public List<NormalExamPaper> getAllPaperInSchdl();

    public void addExamPaper(IExamPaper paper);

    public void deleteExamPaper(IExamPaper paper);

    public void deleteExamPaper(String paperId);

    public IExamPaper queryById(String id);

    public IExamPaper parseAndSave(String filePath) throws FileNotFoundException, ParseException;

    public IExamPaper parseAndSave(InputStream inputStream) throws ParseException;

    public float getTotalAccuracy(IExamPaper paper);

    public void updateAllStudyInfo(String paperId, IQuestion question, boolean isCorrect);
}
