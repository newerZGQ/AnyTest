package com.zgq.wokao.parser.context;

import com.zgq.wokao.parser.context.item.PaperItem;
import com.zgq.wokao.parser.context.item.PaperItemType;

/**
 * Created by zgq on 2017/2/20.
 */

public class PaperContext extends BaseContext implements IPaperContext {
    @Override
    public void deContext() {
        super.deContext();

    }

    public void inContext(PaperItemType type) {
        PaperItem item = new PaperItem(type.getType());
        super.inContext(item);
    }

    @Override
    public void init(int length) {
        super.init(length);
    }

}
