package com.zgq.wokao.ui.presenter.impl;

import com.zgq.wokao.exception.ParseException;
import com.zgq.wokao.model.paper.IExamPaper;

/**
 * Created by zgq on 2017/9/11.
 */

public interface IFileSelectorPresenter {
    IExamPaper parseFromFile(String filePath) throws ParseException;
}
