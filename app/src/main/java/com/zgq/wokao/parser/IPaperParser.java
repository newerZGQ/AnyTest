package com.zgq.wokao.parser;

import com.zgq.wokao.model.paper.ExamPaper;
import com.zgq.wokao.parser.context.IPaperContext;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by zgq on 2017/2/18.
 */

public interface IPaperParser extends IParser{
    public ArrayList<PaperParser.Topic> parse(InputStream is);
}