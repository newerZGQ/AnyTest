package com.zgq.wokao.model.viewdate;

import android.util.Log;

import com.zgq.wokao.Util.LogUtil;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.paper.info.IPaperInfo;
import com.zgq.wokao.model.paper.question.IQuestion;
import com.zgq.wokao.model.paper.question.info.IQuestionInfo;
import com.zgq.wokao.model.paper.question.info.QuestionInfo;

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
            List<QuestionInfo> infos = new ArrayList<>();
            for (IQuestion question : questions){
                infos.add(question.getInfo());
            }

            int studyCount = 0;
            int correctCount = 0;
            int starCount = 0;
            for (IQuestionInfo info: infos){
                studyCount += info.getStudyCount();
                correctCount += info.getCorrectCount();
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                info.setAccuracy(info.getStudyCount() == 0? 1f :info.getCorrectCount()/info.getStudyCount());
                realm.commitTransaction();
                if (info.isStared()) starCount++;
            }

            Collections.sort(questions,new SortQstByAccuracy());
            Log.d(LogUtil.PREFIX+TAG,"----->>" + paperInfo.getId());
            data.setPaperId(paperInfo.getId());
            data.setStudyNum(studyCount);
            data.setStarCount(starCount);
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
            return left.getInfo().getAccuracy() >= right.getInfo().getAccuracy() ? 1 : 0;
        }
    }
}
