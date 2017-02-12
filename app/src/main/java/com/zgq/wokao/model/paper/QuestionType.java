package com.zgq.wokao.model.paper;

/**
 * Created by zgq on 2017/2/11.
 */

public enum QuestionType {
    fillin("填空",1),tf("判断",2),sglc("单选",3),mtlc("多选",4),disc("简答",5);
    private int index;
    private String title;
    private QuestionType(String title,int index){
        this.title = title;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
