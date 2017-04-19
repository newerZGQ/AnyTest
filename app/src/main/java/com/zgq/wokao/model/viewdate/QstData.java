package com.zgq.wokao.model.viewdate;

import android.util.Log;

import com.zgq.wokao.Util.LogUtil;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.paper.info.IPaperInfo;
import com.zgq.wokao.model.paper.question.IQuestion;
import com.zgq.wokao.model.paper.question.info.IQuestionInfo;
import com.zgq.wokao.model.paper.question.info.QuestionInfo;
import com.zgq.wokao.model.paper.question.record.QuestionRecord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.realm.Realm;

/**
 * Created by zgq on 2017/3/19.
 */

public class QstData implements ViewData{

    public static final String TAG = QstData.class.getSimpleName();

    //试卷id
    private String paperId;
    //题目类型
    private QuestionType type = QuestionType.fillin;
    //回答正确次数
    private int correctNum;
    //学习次数
    private int studyNum;
    //正确率
    private float accuracy;
    //易错题
    private List<IQuestion> fallibleQsts;
    //题目数量
    private int qstCount;
    //收藏题目数量
    private int starCount;
    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

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


    public int getCorrectNum() {
        return correctNum;
    }

    public void setCorrectNum(int correctNum) {
        this.correctNum = correctNum;
    }

    public int getQstCount() {
        return qstCount;
    }

    public void setQstCount(int qstCount) {
        this.qstCount = qstCount;
    }

    public int getStarCount() {
        return starCount;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }

    public static class Formator{
        public static QstData format(List<IQuestion> questions, IPaperInfo paperInfo){
            QstData data = new QstData();
            if (questions.size() == 0){
                return data;
            }

            int studyCount = 0;
            int correctCount = 0;
            int starCount = 0;
            for (int i = 0; i< questions.size(); i++){
                studyCount += questions.get(i).getRecord().getStudyNumber();
                correctCount += questions.get(i).getRecord().getCorrectNumber();
                if (questions.get(i).getInfo().isStared()) starCount++;
            }

            Collections.sort(questions,new SortQstByAccuracy());
            data.setPaperId(paperInfo.getId());
            data.setStudyNum(studyCount);
            data.setCorrectNum(correctCount);
            data.setStarCount(starCount);
            data.setQstCount(questions.size());
            data.setAccuracy(studyCount == 0 ? 1f : correctCount/studyCount);
            data.setType(questions.get(0).getInfo().getType());
            if (questions.size() >= 3) {
                data.setFallibleQsts(questions.subList(0, 3));
            }else{
                data.setFallibleQsts(questions);
            }
            return data;
        }
    }

    static class SortQstByAccuracy implements Comparator{

        @Override
        public int compare(Object lhs, Object rhs) {
            IQuestion left = (IQuestion)lhs;
            IQuestion right = (IQuestion)rhs;
            if (left.getRecord().getAccuracy() > right.getRecord().getAccuracy()){
                return 1;
            }else if (left.getRecord().getAccuracy() == right.getRecord().getAccuracy()){
                return 0;
            }else {
                return -1;
            }
        }
    }

    @Override
    public String toString() {
        return "QstData{" +
                "paperId='" + paperId + '\'' +
                ", type=" + type +
                ", studyNum=" + studyNum +
                ", accuracy=" + accuracy +
                ", fallibleQsts=" + fallibleQsts +
                ", qstCount=" + qstCount +
                ", starCount=" + starCount +
                '}';
    }
}
