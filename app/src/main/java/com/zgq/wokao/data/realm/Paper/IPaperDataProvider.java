package com.zgq.wokao.data.realm.Paper;

import com.zgq.wokao.model.ExamPaperInfo;
import com.zgq.wokao.model.NormalExamPaper;

import java.util.List;

/**
 * Created by zgq on 2017/2/11.
 */

public interface IPaperDataProvider {
    public List<NormalExamPaper> getAllPaper();
    public List<ExamPaperInfo> getAllPaperInfo();
}
