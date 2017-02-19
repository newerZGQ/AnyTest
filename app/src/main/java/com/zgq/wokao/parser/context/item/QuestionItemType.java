package com.zgq.wokao.parser.context.item;

/**
 * Created by zhangguoqiang on 2017/2/19.
 */

public enum QuestionItemType {
    number(1),body(2),option(3),answer(4);
    private int type;
    private QuestionItemType(int type){
        this.type = type;
    }
    public int getType(){
        return type;
    }
}
