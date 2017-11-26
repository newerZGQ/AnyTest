package com.zgq.wokao.repository;

import com.google.common.base.Optional;
import com.zgq.wokao.entity.paper.NormalExamPaper;
import com.zgq.wokao.entity.paper.info.ExamPaperInfo;
import com.zgq.wokao.entity.summary.StudySummary;

import javax.annotation.Nonnull;

import io.reactivex.Flowable;
import io.realm.RealmResults;

/**
 * Created by zgq on 2017/11/20.
 */

public interface PaperDataSource {
    void saveExamPaper(NormalExamPaper examPaper);
    void deleteExamPaper(NormalExamPaper examPaper);
    Flowable<Optional<NormalExamPaper>> queryPaper(String paperId);
    Flowable<RealmResults<NormalExamPaper>> getAllExamPaper();
    @Nonnull
    Flowable<RealmResults<ExamPaperInfo>> getAllExamPaperInfo();
    void setSked(NormalExamPaper paper, boolean skedState);

    void saveSummary(@Nonnull StudySummary studySummary);
    Flowable<Optional<StudySummary>> getStudySummary();
}
