package com.zgq.wokao.ui.presenter.impl;

import com.zgq.wokao.action.parser.IParserAction;
import com.zgq.wokao.action.parser.ParserAction;
import com.zgq.wokao.exception.ParseException;
import com.zgq.wokao.model.paper.IExamPaper;
import com.zgq.wokao.ui.view.IFileSelectorView;

/**
 * Created by zgq on 2017/9/11.
 */

public class FileSelectorPresenter implements IFileSelectorPresenter {
    private IFileSelectorView selectorView;

    public FileSelectorPresenter(IFileSelectorView selectorView) {
        this.selectorView = selectorView;
    }

    @Override
    public IExamPaper parseFromFile(String filePath) {
        try {
            return ParserAction.getInstance(new ParserAction.ParseResultListener() {
                @Override
                public void onParseSuccess(String paperId) {
                    selectorView.notifyParseSuccess();
                }

                @Override
                public void onParseError(String error) {
                    selectorView.notifyParseFailed();
                }
            }).parseFromFile(filePath);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
