package com.zgq.wokao.action.parser;


import com.zgq.wokao.exception.ParseException;
import com.zgq.wokao.model.paper.IExamPaper;
import com.zgq.wokao.parser.ParserHelper;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by zgq on 2017/2/28.
 */

public class ParserAction implements IParser {

    private ParserHelper parserHelper = ParserHelper.getInstance();


    @Override
    public IExamPaper parseFromFile(String fileString) throws ParseException {

        try {
            return parserHelper.parse(fileString);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public IExamPaper parseFromIS(InputStream inputStream) {
        try {
            return parserHelper.parse(inputStream);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public IExamPaper parseFromString(String content) {
        return null;
    }
}
