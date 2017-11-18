package com.zgq.wokaofree.parser.context;

import com.zgq.wokaofree.parser.context.item.PaperItem;
import com.zgq.wokaofree.parser.context.item.PaperItemType;

/**
 * Created by zgq on 2017/2/20.
 */

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
