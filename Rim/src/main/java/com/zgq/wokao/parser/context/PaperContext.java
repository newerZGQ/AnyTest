package com.zgq.wokao.parser.context;

import com.zgq.wokao.parser.context.item.PaperItem;
import com.zgq.wokao.parser.context.item.PaperItemType;

public class PaperContext extends BaseContext implements IPaperContext {
    @Override
    public void deContext() {
        super.deContext();
    }

    public void inContext(PaperItemType type) {
        PaperItem item = new PaperItem(type);
        super.inContext(item);
    }

    @Override
    public void init(int length) {
        super.init(length);
    }

}
