package com.zgq.wokao.ui.presenter.impl;

import android.util.Log;

import com.zgq.wokao.action.paper.IPaperAction;
import com.zgq.wokao.action.paper.impl.PaperAction;
import com.zgq.wokao.action.parser.IParserAction;
import com.zgq.wokao.action.parser.ParserAction;
import com.zgq.wokao.exception.ParseException;
import com.zgq.wokao.model.paper.IExamPaper;
import com.zgq.wokao.ui.presenter.IHomePrerenter;
import com.zgq.wokao.ui.view.IHomeView;

/**
 * Created by zgq on 2017/3/4.
 */

public class HomePresenterImpl implements IHomePrerenter{

    private IHomeView homeView;
    private IPaperAction paperAction = PaperAction.getInstance();
    private IParserAction parserAction = ParserAction.getInstance();
    public HomePresenterImpl(IHomeView homeView){
        this.homeView = homeView;
        initListener();
    }

    private void initListener(){
        parserAction.setListener(new ParserAction.ParseResultListener() {
            @Override
            public void onParseSuccess(String paperId) {
                Log.d("------>>","parsesuccess");
                homeView.notifyDataChanged();
                homeView.hideLoadingView();
                homeView.hideProgressBar();
            }

            @Override
            public void onParseError(String error) {
                //show error toast
            }
        });
    }
    public void updateSlideUp(){
        homeView.updateSlideUp();
    }
    public void goSearch(){
        homeView.goSearch();
    }
    public void showScheduleFragment(){
        homeView.showScheduleFragment();
    }
    public void showPapersFragment(){
        homeView.showPapersFragment();
    }
    @Override
    public IExamPaper parseFromFile(String filePath) {
        try {
            return parserAction.parseFromFile(filePath);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
