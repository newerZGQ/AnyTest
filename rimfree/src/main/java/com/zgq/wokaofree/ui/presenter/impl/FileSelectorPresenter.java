package com.zgq.wokaofree.ui.presenter.impl;

import com.zgq.wokaofree.action.parser.IParserAction;
import com.zgq.wokaofree.action.parser.ParserAction;
import com.zgq.wokaofree.exception.ParseException;
import com.zgq.wokaofree.model.paper.IExamPaper;
import com.zgq.wokaofree.ui.view.IFileSelectorView;

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
        if (!checkFileAvaliable(filePath)){
            selectorView.notifyParseFailed();
        }
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
        } catch (Exception e) {
            selectorView.notifyParseFailed();
        }
        return null;
    }

    private boolean checkFileAvaliable(String path){
        return path.toLowerCase().endsWith(".txt") || path.toLowerCase().endsWith(".doc");
    }
}
