package com.zgq.wokao.action.paper;

import com.zgq.wokao.model.paper.IExamPaper;

import java.io.InputStream;

/**
 * Created by zgq on 2017/2/28.
 */

public interface IParser {
    public IExamPaper parseFromFile(String filePath);
    public IExamPaper parseFromIS(InputStream inputStream);
    public IExamPaper parseFromString(String content);
}
