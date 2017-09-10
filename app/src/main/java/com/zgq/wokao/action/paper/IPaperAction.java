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
    List<NormalExamPaper> getAllPaper();

    List<NormalExamPaper> getAllPaperInSchdl();

    void addExamPaper(IExamPaper paper);

    void deleteExamPaper(IExamPaper paper);

    void deleteExamPaper(String paperId);

    IExamPaper queryById(String id);

    IExamPaper parseAndSave(String filePath) throws FileNotFoundException, ParseException;

    IExamPaper parseAndSave(InputStream inputStream) throws ParseException;

    float getTotalAccuracy(IExamPaper paper);

    void updateAllStudyInfo(String paperId, IQuestion question, boolean isCorrect);
}
