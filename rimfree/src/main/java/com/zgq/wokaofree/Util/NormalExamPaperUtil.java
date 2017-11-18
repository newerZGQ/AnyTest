package com.zgq.wokaofree.Util;

import com.zgq.wokaofree.model.paper.NormalExamPaper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by zgq on 16-7-20.
 */
public class NormalExamPaperUtil {
    public static void sortPapers(ArrayList<NormalExamPaper> papers) {
        Collections.sort(papers, new SortPapersByLastStudyDate());
    }

    public static class SortPapersByLastStudyDate implements Comparator {
        @Override
        public int compare(Object lhs, Object rhs) {
            NormalExamPaper paper1 = (NormalExamPaper) lhs;
            NormalExamPaper paper2 = (NormalExamPaper) rhs;
            return -paper1.getPaperInfo().getLastStudyDate().compareTo(paper2.getPaperInfo().getLastStudyDate());
        }
    }
}