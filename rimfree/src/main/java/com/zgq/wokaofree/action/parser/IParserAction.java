package com.zgq.wokaofree.action.parser;

import com.zgq.wokaofree.exception.ParseException;
import com.zgq.wokaofree.model.paper.IExamPaper;

import java.io.InputStream;

/**
 * Created by zgq on 2017/2/28.
 */

public interface IParserAction {
    IExamPaper parseFromFile(String filePath) throws ParseException, Exception;

    IExamPaper parseFromIS(InputStream inputStream);

    IExamPaper parseFromString(String content);
}
