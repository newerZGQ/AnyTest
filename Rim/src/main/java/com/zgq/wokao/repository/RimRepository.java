package com.zgq.wokao.repository;

import com.zgq.wokao.entity.paper.NormalExamPaper;

import com.google.common.base.Optional;
import com.zgq.wokao.entity.summary.StudySummary;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

public class RimRepository implements RimDataSource {

    private PaperRepository paperRepository;

    @Inject
    public RimRepository(PaperRepository paperRepository){
        this.paperRepository = paperRepository;
    }

    @Override
    public void saveExamPaper(NormalExamPaper examPaper) {
        paperRepository.saveExamPaper(examPaper);
    }

    @Override
    public void deleteExamPaper(NormalExamPaper examPaper) {
        paperRepository.deleteExamPaper(examPaper);
    }

    @Override
    public Flowable<Optional<NormalExamPaper>> queryPaper(String paperId) {
        return paperRepository.queryPaper(paperId);
    }

    @Override
    public void setSked(NormalExamPaper paper, boolean skedState) {
        paperRepository.setSked(paper, skedState);
    }

    @Override
    public void saveSummary(@Nonnull final StudySummary studySummary) {
        getStudySummary().subscribe(new Consumer<Optional<StudySummary>>() {
            @Override
            public void accept(Optional<StudySummary> studySummaryOptional) throws Exception {
                if (!studySummaryOptional.isPresent()){
                    paperRepository.saveSummary(studySummary);
                }
            }
        });
    }

    @Override
    public Flowable<Optional<StudySummary>> getStudySummary() {
        return paperRepository.getStudySummary();
    }
}
