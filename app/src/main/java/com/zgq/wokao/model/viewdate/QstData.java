package com.zgq.wokao.model.viewdate;

import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.paper.question.IQuestion;
import com.zgq.wokao.model.paper.question.info.QuestionInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by zgq on 2017/3/19.
 */

public class QstData implements ViewData{
    //题目类型
    private QuestionType type;
    //学习次数
    private int studyNum;
    //正确率
    private float accuracy;
    //易错题
    private List<IQuestion> fallibleQsts;

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public int getStudyNum() {
        return studyNum;
    }

    public void setStudyNum(int studyNum) {
        this.studyNum = studyNum;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public List<IQuestion> getFallibleQsts() {
        return fallibleQsts;
    }

    public void setFallibleQsts(List<IQuestion> fallibleQsts) {
        this.fallibleQsts = fallibleQsts;
    }

    public static class Formator{
        public static QstData format(List<IQuestion> questions){
            QstData data = new QstData();
            if (questions.size() == 0){
                return data;
            }
            List<QuestionInfo> infos = new ArrayList<>();
            for (IQuestion question : questions){
                infos.add(question.getInfo());
            }

            int studyCount = 0;
            int correctCount = 0;

            for (QuestionInfo info: infos){
                studyCount += info.getStudyCount();
                correctCount += info.getCorrectCount();
                info.setAccuracy(info.getStudyCount() == 0? 1f :info.getCorrectCount()/info.getStudyCount());
            }

            Collections.sort(questions,new SortQstByAccuracy());

            data.setAccuracy(studyCount == 0 ? 1f : correctCount/studyCount);
            data.setType(questions.get(0).getInfo().getType());
            data.setFallibleQsts(questions.subList(0,3));
            return data;
        }
    }

    static class SortQstByAccuracy implements Comparator{

        @Override
        public int compare(Object lhs, Object rhs) {
            IQuestion left = (IQuestion)lhs;
            IQuestion right = (IQuestion)rhs;
            return left.getAccuracy() >= right.getAccuracy() ? 1 : 0;
        }
    }
}
