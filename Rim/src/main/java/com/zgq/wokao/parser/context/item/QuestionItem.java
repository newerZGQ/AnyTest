package com.zgq.wokao.parser.context.item;

public class QuestionItem implements ParserItem {
    private QuestionItemType type;

    public QuestionItem(QuestionItemType type) {
        this.type = type;
    }

    public QuestionItemType getType() {
        return type;
    }
}
