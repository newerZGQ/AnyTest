package com.zgq.wokao.Util;

import com.zgq.wokao.model.paper.NormalIExamPaper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by zgq on 16-7-20.
 */
public class NormalExamPaperUtil {
    public static void sortPapers(ArrayList<NormalIExamPaper> papers){
        Collections.sort(papers,new SortPapersByLastStudyDate());
    }

    public static class SortPapersByLastStudyDate implements Comparator{
        @Override
        public int compare(Object lhs, Object rhs) {
            NormalIExamPaper paper1 = (NormalIExamPaper)lhs;
            NormalIExamPaper paper2 = (NormalIExamPaper)rhs;
            return -paper1.getPaperInfo().getLastStudyDate().compareTo(paper2.getPaperInfo().getLastStudyDate());
        }
    }
}
