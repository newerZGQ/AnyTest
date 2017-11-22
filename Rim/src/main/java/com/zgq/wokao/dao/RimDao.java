package com.zgq.wokao.dao;

import com.zgq.wokao.entity.paper.NormalExamPaper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by zgq on 2017/11/22.
 */

public interface RimDao {
    void saveExamPaper(@Nonnull NormalExamPaper paper);
    void deleteExamPaper(@Nonnull NormalExamPaper paper);
    @Nullable
    NormalExamPaper queryExamPaper(@Nonnull String paperId);
    void setSked(@Nonnull NormalExamPaper paper, boolean skedState);
}
