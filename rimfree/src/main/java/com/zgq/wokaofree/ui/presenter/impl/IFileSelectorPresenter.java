package com.zgq.wokaofree.ui.presenter.impl;

import com.zgq.wokaofree.exception.ParseException;
import com.zgq.wokaofree.model.paper.IExamPaper;

/**
 * Created by zgq on 2017/9/11.
 */

public interface IFileSelectorPresenter {
    IExamPaper parseFromFile(String filePath) throws ParseException;
}
