package com.zgq.wokao.action.parser;

import com.zgq.wokao.exception.ParseException;
import com.zgq.wokao.model.paper.IExamPaper;

import java.io.InputStream;

/**
 * Created by zgq on 2017/2/28.
 */

public interface IParserAction {
    public IExamPaper parseFromFile(String filePath) throws ParseException;

    public IExamPaper parseFromIS(InputStream inputStream);

    public IExamPaper parseFromString(String content);
}
