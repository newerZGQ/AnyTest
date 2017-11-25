package com.zgq.wokao.module.home;

import com.orhanobut.logger.Logger;
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
                .subscribe(papers -> {
                    if (papers.size() == 0){
                        view.showEmptyView();
                    }else {
                        view.setPaperListData(papers);
                    }
                });
    }

    @Override
    public void deletePaper(NormalExamPaper paper) {
        repository.deleteExamPaper(paper);
        view.notifyDataChanged();
    }

    @Override
    public void removeFromSchedule(NormalExamPaper paper) {
        repository.setSked(paper, false);
        view.notifyDataChanged();
    }

    @Override
    public void addToSchedule(NormalExamPaper paper) {
        repository.setSked(paper, true);
        view.notifyDataChanged();
    }
}
