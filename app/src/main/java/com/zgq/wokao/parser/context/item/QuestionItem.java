package com.zgq.wokao.parser.context.item;

/**
 * Created by zhangguoqiang on 2017/2/19.
 */

public class QuestionItem implements ParserItem {
    private QuestionItemType type;

    public QuestionItem(QuestionItemType type) {
        this.type = type;
    }

    public QuestionItemType getType() {
        return type;
    }
}
