package com.zgq.wokao.action.paper;


import com.zgq.wokao.model.paper.IExamPaper;

import java.io.InputStream;

/**
 * Created by zgq on 2017/2/28.
 */

public class ParserAction implements IParser {

    @Override
    public IExamPaper parseFromFile(String filePath) {
        return null;
    }

    @Override
    public IExamPaper parseFromIS(InputStream inputStream) {
        return null;
    }

    @Override
    public IExamPaper parseFromString(String content) {
        return null;
    }
}
