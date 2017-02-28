package com.zgq.wokao.action.paper;

import android.renderscript.ScriptGroup;

import com.zgq.wokao.exception.ParseException;
import com.zgq.wokao.model.paper.IExamPaper;
import com.zgq.wokao.model.paper.NormalIExamPaper;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by zgq on 2017/2/28.
 */

public interface IPaperAction extends IPaperInfoAction,IPaperSchdlAction{
    public void addExamPaper(IExamPaper paper);
    public void deleteExamPaper(IExamPaper paper);
    public IExamPaper queryById(String id);
    public IExamPaper parseAndSave(String filePath) throws FileNotFoundException, ParseException;
    public IExamPaper parseAndSave(InputStream inputStream) throws ParseException;

}
