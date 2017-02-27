package com.zgq.wokao.model.paper;

import io.realm.RealmObject;

/**
 * Created by zgq on 2017/2/11.
 */

public class QuestionType extends RealmObject {
    public static final int noqst_index = 0;
    public static final int fillin_index = 1;
    public static final int tf_index = 2;
    public static final int sglc_index = 3;
    public static final int mtlc_index = 4;
    public static final int disc_index = 5;
    public static QuestionType notQst = new QuestionType("非题目" ,noqst_index);
    public static QuestionType fillin = new QuestionType("填空", fillin_index);
    public static QuestionType tf = new QuestionType("判断", tf_index);
    public static QuestionType sglc = new QuestionType("单选", sglc_index);
    public static QuestionType mtlc = new QuestionType("多选", mtlc_index);
    public static QuestionType disc = new QuestionType("简答", disc_index);



    private String name;
    private int index;

    public QuestionType() {
    }

    public QuestionType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
