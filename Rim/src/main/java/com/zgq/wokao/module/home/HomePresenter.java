package com.zgq.wokao.module.home;

import com.google.common.base.Optional;
import com.zgq.wokao.entity.summary.StudySummary;
import com.zgq.wokao.module.base.BasePresenter;
import com.zgq.wokao.repository.RimRepository;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class HomePresenter extends BasePresenter<HomeContract.MainView>
        implements HomeContract.MainPresenter {

    private RimRepository repository;

    @Inject
    public HomePresenter(RimRepository repository){
        this.repository = repository;
    }

    @Override
    public void loadSummary() {
        repository.getStudySummary().subscribe(new Consumer<Optional<StudySummary>>() {
            @Override
            public void accept(Optional<StudySummary> studySummaryOptional) throws Exception {
                if (studySummaryOptional.isPresent()){
                    StudySummary persist = studySummaryOptional.get();
                    repository.copyFromRealm(persist)
                            .subscribe(free -> view.showTotalRecord(free));
                }
            }
        });
    }

    @Override
    public void subscribe() {
        super.subscribe();
        loadSummary();
    }
}
