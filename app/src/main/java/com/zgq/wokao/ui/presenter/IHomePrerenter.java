package com.zgq.wokao.ui.presenter;

import com.zgq.wokao.exception.ParseException;
import com.zgq.wokao.model.paper.IExamPaper;

/**
 * Created by zgq on 2017/3/4.
 */

public interface IHomePrerenter {
    public IExamPaper parseFromFile(String filePath) throws ParseException;

}
