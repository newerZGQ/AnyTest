package com.zgq.wokaofree.parser.context;

import com.zgq.wokaofree.parser.context.item.QuestionItem;
import com.zgq.wokaofree.parser.context.item.QuestionItemType;

/**
 * Created by zhangguoqiang on 2017/2/19.
 */

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
