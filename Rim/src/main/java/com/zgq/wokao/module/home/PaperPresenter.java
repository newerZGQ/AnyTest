package com.zgq.wokao.module.home;

import com.zgq.wokao.entity.paper.NormalExamPaper;
import com.zgq.wokao.module.base.BasePresenter;
import com.zgq.wokao.repository.RimDataSource;
import com.zgq.wokao.repository.RimRepository;

import javax.inject.Inject;

public class PaperPresenter extends BasePresenter<HomeContract.PaperView>
        implements HomeContract.PaperPresenter {

    private RimDataSource repository;

    @Inject
    public PaperPresenter(RimRepository repository){
        this.repository = repository;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        loadAllPapers();
    }

    @Override
    public void loadAllPapers() {
        repository.getAllExamPaper()
                .subscribe(papers -> view.setPaperListData(papers));
    }

    @Override
    public void deletePaper(NormalExamPaper paper) {
        repository.deleteExamPaper(paper);
    }

    @Override
    public void removeFromSchedule(NormalExamPaper paper) {

    }

    @Override
    public void addToSchedule(NormalExamPaper paper) {

    }
}
