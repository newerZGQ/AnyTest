package com.zgq.wokao.parser.context;

import com.zgq.wokao.parser.context.item.QuestionItem;
import com.zgq.wokao.parser.context.item.QuestionItemType;

public class QuestionContext extends BaseContext implements IQuestionContext {
    @Override
    public void deContext() {
        super.deContext();
    }

    public void inContext(QuestionItemType type) {
        QuestionItem item = new QuestionItem(type);
        super.inContext(item);
    }

    @Override
    public void init(int length) {
        super.init(length);
    }
}
