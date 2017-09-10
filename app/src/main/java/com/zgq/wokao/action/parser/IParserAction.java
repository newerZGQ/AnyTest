package com.zgq.wokao.action.parser;

import com.zgq.wokao.exception.ParseException;
import com.zgq.wokao.model.paper.IExamPaper;

import java.io.InputStream;

/**
 * Created by zgq on 2017/2/28.
 */

public interface IParserAction {
    IExamPaper parseFromFile(String filePath) throws ParseException;

    IExamPaper parseFromIS(InputStream inputStream);

    IExamPaper parseFromString(String content);
}
