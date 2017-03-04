package com.zgq.wokao.ui.presenter.impl;

import com.zgq.wokao.action.paper.IPaperAction;
import com.zgq.wokao.action.parser.IParserAction;
import com.zgq.wokao.action.parser.ParserAction;
import com.zgq.wokao.model.paper.IExamPaper;
import com.zgq.wokao.ui.presenter.IHomePrerenter;
import com.zgq.wokao.ui.view.IHomeView;

/**
 * Created by zgq on 2017/3/4.
 */

public class HomePresenterImpl implements IHomePrerenter{

    private IHomeView homeView;
    private IPaperAction paperAction;
    private IParserAction parserAction;
    public HomePresenterImpl(IHomeView homeView, IPaperAction paperAction, IParserAction parserAction){
        this.homeView = homeView;
        this.paperAction = paperAction;
        this.parserAction = parserAction;
        init();
    }

    private void init(){
        parserAction.setListener(new ParserAction.ParseResultListener() {
            @Override
            public void onParseSuccess(String paperId) {
                homeView.notifyDataChanged();
            }

            @Override
            public void onParseError(String error) {
                //show error toast
            }
        });
    }
    public void showSlideUp(){
        homeView.showSlideUp();
    }
    public void hideSlideUp(){
        homeView.hideSlideUp();
    }
    public void goSearch(){
        homeView.goSerach();
    }
    public void showScheduleFragment(){
        homeView.showScheduleFragment();
    }
    public void showPapersFragment(){
        homeView.showPapersFragment();
    }
    @Override
    public IExamPaper parseFromFile(String filePath){
        return null;
    }
}
